package com.jrdcom.simulatetest.utils;

import android.util.Log;

import com.jrdcom.simulatetest.beans.PlfInfoBean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by user on 4/1/17.
 */
public class ParsePlfUtil {

    private static final String TAG = "ParsePlfUtil";
    private static final String PLF_DIR = "/system/vendor/etc/";

    //plf node name
    private static final String NODE_NAME_SIMPLE_VAR = "SIMPLE_VAR";
    private static final String NODE_NAME_SDMID = "SDMID";
    private static final String NODE_NAME_METATYPE = "METATYPE";
    private static final String NODE_NAME_VALUE = "VALUE";
    private static final String NODE_NAME_FEATURE = "FEATURE";
    private static final String NODE_NAME_DESC = "DESC";


    public static List<PlfInfoBean> readAllPlfData() {
        List<PlfInfoBean> plfDates = new ArrayList<>();
        File dir = new File(PLF_DIR);
        String[] files = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;


        factory.setValidating(false);
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        if (dir.isDirectory()) {
            files = dir.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".plf")) {
                        return true;
                    }
                    return false;
                }
            });
        }
        if (files != null && documentBuilder != null) {
            FileInputStream fis;
            Document document;
            Element rootConf;
            NodeList recordList = null;
            Node record;
            NodeList childNodes;
            int recordLen;
            int childLen;
            String nodeName;
            String nodeValue;
            String isdmId = null;
            String metaType = null;
            String feature = null;
            String description = null;
            String value = null;

            for (String fileName : files) {
                try {
                    fis = new FileInputStream(PLF_DIR + fileName);
                    document = documentBuilder.parse(fis);
                    rootConf = document.getDocumentElement();
                    recordList = rootConf.getElementsByTagName(NODE_NAME_SIMPLE_VAR);
                    Log.d(TAG, "readAllPlfData: recordList length = " + recordList.getLength());
                } catch (SAXException | IOException e) {
                    e.printStackTrace();
                }
                if (recordList != null && recordList.getLength() > 0) {
                    recordLen = recordList.getLength();
                    //loop all simple val list
                    for (int i = 0; i < recordLen; i++) {
                        record = recordList.item(i);
                        childNodes = record.getChildNodes();
                        childLen = childNodes.getLength();
                        Log.d(TAG, "readAllPlfData: childLen = "+childLen);
                        if (childLen > 0) {
                            //loop one record
                            for (int j = 0; j < childLen; j++) {
                                nodeName = childNodes.item(j).getNodeName();
                                nodeValue = childNodes.item(j).getTextContent();
                                switch (nodeName) {
                                    case NODE_NAME_SDMID:
                                        isdmId = nodeValue;
                                        break;
                                    case NODE_NAME_METATYPE:
                                        metaType = nodeValue;
                                        break;
                                    case NODE_NAME_VALUE:
                                        value = nodeValue;
                                        break;
                                    case NODE_NAME_FEATURE:
                                        feature = nodeValue;
                                        break;
                                    case NODE_NAME_DESC:
                                        description = nodeValue;
                                        break;
                                }
                            }
                        }
                        plfDates.add(new PlfInfoBean(-1,metaType,isdmId,feature,description,value));
                    }
                }

            }
        } else {
            Log.d(TAG, "readAllPlfData: no plf file in directory!");
        }
        Log.d(TAG, "readAllPlfData: plfDates.size = "+plfDates.size());
        return plfDates;
    }
}
