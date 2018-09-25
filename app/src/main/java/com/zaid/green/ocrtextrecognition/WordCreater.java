package com.zaid.green.ocrtextrecognition;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class WordCreater extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_creater);
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    //    DocFile app = new DocFile();
    //    try {
    //        app.newWordDoc("testfile", "Hi how r u?", path);
    //    } catch (Exception e) {
     //       e.printStackTrace();
    //    }
    }

  /*  public class DocFile {
        public void newWordDoc(String filename, String fileContent, String path)
                throws Exception {
            XWPFDocument document = new XWPFDocument();
            XWPFParagraph tmpParagraph = document.createParagraph();
            XWPFRun tmpRun = tmpParagraph.createRun();
            tmpRun.setText(fileContent);
            tmpRun.setFontSize(18);
            FileOutputStream fos = new FileOutputStream(new File(path+ "/" +filename + ".docx"));
            document.write(fos);
            fos.close();
        } */
    }

