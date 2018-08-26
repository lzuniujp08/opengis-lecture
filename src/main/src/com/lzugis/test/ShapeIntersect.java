package com.lzugis.test;

import com.alibaba.fastjson.JSONObject;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ShapeIntersect {

    public static void main(String[] args){
        double start = System.currentTimeMillis();

        String inputPath1 = "D:\\data\\province.shp",
                inputPath2 = "D:\\data\\test.shp",
                outputPath = "D:\\data\\province_test.shp";

        try {
            File inputFile1 = new File(inputPath1);
            File inputFile2 = new File(inputPath2);

            ShapefileDataStore shpDataStore1 = new ShapefileDataStore(inputFile1.toURL());
            ShapefileDataStore shpDataStore2 = new ShapefileDataStore(inputFile2.toURL());

            //设置编码
            Charset charset = Charset.forName("GBK");
            shpDataStore1.setCharset(charset);
            shpDataStore2.setCharset(charset);

            String typeName1 = shpDataStore1.getTypeNames()[0];
            String typeName2 = shpDataStore2.getTypeNames()[0];

            SimpleFeatureSource featureSource1 = shpDataStore1.getFeatureSource(typeName1);
            SimpleFeatureSource featureSource2 = shpDataStore2.getFeatureSource(typeName2);

            SimpleFeatureCollection featureCollection1 = featureSource1.getFeatures();
            SimpleFeatureCollection featureCollection2 = featureSource2.getFeatures();

            SimpleFeatureIterator itertor1 = featureCollection1.features();

            //创建输出文件
            File outputFile = new File(outputPath);
            Map<String, Serializable> params = new HashMap<String, Serializable>();
            params.put( ShapefileDataStoreFactory.URLP.key, outputFile.toURI().toURL() );
            ShapefileDataStore ds = (ShapefileDataStore) new ShapefileDataStoreFactory().createNewDataStore(params);
            //定义图形信息和属性信息
            SimpleFeatureTypeBuilder tb = new SimpleFeatureTypeBuilder();
            tb.setCRS(DefaultGeographicCRS.WGS84);
            tb.setName("shapefile");
            tb.add("the_geom", MultiPolygon.class);
            tb.add("name", String.class);
            ds.createSchema(tb.buildFeatureType());
            //设置编码
            ds.setCharset(charset);
            //设置Writer
            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(ds.getTypeNames()[0], Transaction.AUTO_COMMIT);

            JSONObject jsonObject = new JSONObject();
            //开始计算
            while (itertor1.hasNext()) {
                SimpleFeature feature1 = itertor1.next();
                Geometry geom1 = (Geometry) feature1.getDefaultGeometry();
                String name = feature1.getAttribute("NAME").toString();
                String id1 = feature1.getID();

                SimpleFeatureIterator itertor2 = featureCollection2.features();
                while (itertor2.hasNext()){
                    SimpleFeature feature2 = itertor2.next();
                    Geometry geom2 = (Geometry) feature2.getDefaultGeometry();
                    String id2 = feature2.getID();
                    boolean isDone1 = jsonObject.containsKey(id1+"-"+id2),
                        isDone2 = jsonObject.containsKey(id2+"-"+id1),
                        isIntersect = geom1.intersects(geom2);
                    if(!isDone1 && !isDone2 && isIntersect){
                        Geometry geomOut = geom1.intersection(geom2);
                        SimpleFeature featureOut = writer.next();
                        featureOut.setAttribute("the_geom", geomOut);
                        featureOut.setAttribute("name", name);
                        writer.write();
                    }
                    jsonObject.put(id1+"-"+id2, true);
                    jsonObject.put(id2+"-"+id1, true);
                }
                itertor2.close();
            }

            writer.close();
            ds.dispose();

            itertor1.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        double end = System.currentTimeMillis();
        System.out.println("共耗时"+(end-start)+"MS");
    }
}
