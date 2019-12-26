package com.llf.ssm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.json.JSONObject;

public class WordUtil {
		
	
	public static File createWordByPio(String srcPath, String destPath, String jsonStringData) throws IOException {
		File downFile = null;
		
		JSONObject json = new JSONObject(jsonStringData);
		String[] sp = srcPath.split("\\.");
		String[] dp = destPath.split("\\.");
		if((sp.length > 0) && (dp.length > 0)) {
			
			if(sp[sp.length - 1].equalsIgnoreCase("docx")) {
				// 比较文件扩展 docx
				XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
				// 替换段落中指定文字
				Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
				while(itPara.hasNext()) {
					XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
					List<XWPFRun> runs = paragraph.getRuns();
					for(int i = 0; i < runs.size(); i++) {
						String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
						for (Iterator iter = json.keys(); iter.hasNext();) {
							String key = (String) iter.next();
							String val = json.getString(key);
							oneparaString = oneparaString.replace("｛" + key + "｝", val);
						}
						runs.get(i).setText(oneparaString, 0);
					}
				}
				// 替换表格中的指定文字
				Iterator<XWPFTable> itTable = document.getTablesIterator();
				while(itTable.hasNext()) {
					XWPFTable table = (XWPFTable) itTable.next();
					int rcount = table.getNumberOfRows();
					for(int i = 0; i < rcount; i++) {
						XWPFTableRow row = table.getRow(i);
						List<XWPFTableCell> cells = row.getTableCells();
						for(XWPFTableCell cell : cells) {
							String cellTextString = cell.getText();
							for (Iterator iter = json.keys(); iter.hasNext();) {
								String key = (String) iter.next();
								String val = json.getString(key);
								cellTextString = cellTextString.replace("｛" + key + "｝", val);
							}
							cell.removeParagraph(0);
							cell.setText(cellTextString);
						}
					}
				}
				FileOutputStream outStream = null;
				try {
					document.write(outStream);
				} finally {
					// TODO: handle finally clause
					if(outStream != null) {
						outStream.close();
					}				
				}
				downFile = new File(destPath);
				
			} else if ((sp[sp.length - 1].equalsIgnoreCase("doc")) && (dp[dp.length - 1].equalsIgnoreCase("doc")) ) {				
				// doc 生成目标文件必须是 doc
				
				HWPFDocument document = null;				
				document = new HWPFDocument(new FileInputStream(srcPath));
				Range range = document.getRange();
				for (Iterator iter = json.keys(); iter.hasNext();) {
					String key = (String) iter.next();
					String val = json.getString(key);
					range.replaceText("｛" + key + "｝", val);
				}
				FileOutputStream outStream = null;
				outStream = new FileOutputStream(destPath);
				document.write(outStream);
				outStream.close();
				downFile = new File(destPath);			
			}
		} 
		return downFile;
	}

	public static String ReadContent(File destFile, String[] startWords, String[] endWords) throws IOException, XmlException, OpenXML4JException, NotOLE2FileException{
		String result = null;
		//使用poi读取word文档，读取.doc文件
		if(destFile!=null && destFile.toString().endsWith(".doc")){
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(destFile));
	        WordExtractor we = new WordExtractor(bis);
	        result = we.getText();
	        bis.close();
	    //使用poi读取word文档，读取.docx文件
		}else if(destFile!=null && destFile.toString().endsWith(".docx")){
			OPCPackage opc = POIXMLDocument.openPackage(destFile.toString());  
	        POIXMLTextExtractor poi = new XWPFWordExtractor(opc);  
	        result = poi.getText();	 
	        opc.close();
		}
		if(result != null) {
			for (String startWord : startWords) {
				int indexStart = result.indexOf(startWord);
				if(indexStart >= 0) {
					result = result.substring(indexStart, result.length());
					break;
				}
			}
			for (String endWord : endWords) {
				int indexEnd = result.lastIndexOf(endWord);
				if(indexEnd >= 0) {
					result = result.substring(0, indexEnd);
					break;
				}
			}
			result = "    " + result.replace(System.lineSeparator(), System.lineSeparator() + "    ");
		}
		return result;
	}
}
