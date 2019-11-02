package app.aplicaciones.pdfreadersimple;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PDF_CODE = 1000 ;
    Button btn_open_storage;
    //Atento coneste value lo puse para que se quite el error pero siento que no se soluciono


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Request Read & Write External Storage
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new BaseMultiplePermissionsListener(){
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        super.onPermissionsChecked(report);
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        super.onPermissionRationaleShouldBeShown(permissions, token);
                    }
                }).check();

        btn_open_storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserPDF=new Intent(Intent.ACTION_GET_CONTENT);
                browserPDF.setType("application/pdf");
                browserPDF.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(browserPDF,"Select PDF"),PICK_PDF_CODE);
            }
        });
    }
    private void createPDFFile(String path) {
        if(new File(path).exists())
            new File(path).delete();
        try {
            Document document=new Document();
            //Save
            PdfWriter.getInstance(document,new FileOutputStream(path));
            //Abrir Documento
            document.open();
            //Configuraciones
            document.setPageSize(PageSize.A4);
            document.addCreationDate();
            document.addAuthor("Xhinz");
            document.addCreator("Leonel Alvarez");

            //Tipo de Letra
            float fontSize=20.0f;
            //Fuente Personalizada
            BaseFont fontName=BaseFont.createFont("assets/fonts/brandon_medium.otf","UTF-8",BaseFont.EMBEDDED);
            //Crear titulo del documento
            Font titleFont=new Font(fontName,36.0f,Font.NORMAL,BaseColor.BLACK);
            addNewItem(document,"Order Details", Element.ALIGN_CENTER,titleFont);
            //Añadir Detalles
            Font orderNumberFont =new Font(fontName,36.0f,Font.NORMAL,BaseColor.BLACK);
            addNewItem(document,"Order No",Element.ALIGN_LEFT,orderNumberFont);
            Font orderNumberValueFont = new Font(fontName,36.0f , Font.NORMAL,BaseColor.BLACK);
            addNewItem(document,"#717171",Element.ALIGN_LEFT,orderNumberValueFont);
            addLineSeparator(document);
            addNewItem(document,"Order Date",Element.ALIGN_LEFT,orderNumberFont);
            addNewItem(document,"28/10/2019",Element.ALIGN_LEFT,orderNumberValueFont);
            addLineSeparator(document);
            addNewItem(document,"Account Name",Element.ALIGN_LEFT,orderNumberFont);
            addNewItem(document,"Leonel Alvarez",Element.ALIGN_LEFT,orderNumberValueFont);
            addLineSeparator(document);
            //Añadir Producto orden y detalle
            addNewItem(document,"Product Detail",Element.ALIGN_CENTER,titleFont);
            addLineSeparator(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    private void addLineSeparator(Document document) throws DocumentException {
        document.add(new Paragraph(""));
    }
    private void addNewItem(Document document, String text, int aling, Font font)throws DocumentException {
        Chunk chunk =new Chunk(text,font);
        Paragraph paragraph=new Paragraph(chunk);
        paragraph.setAlignment(aling);
        document.add(paragraph);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_PDF_CODE && resultCode== RESULT_OK && data !=null)
        {
            Uri selectedPDF=data.getData();
            Intent intent=new Intent(MainActivity.this,ViewActivity.class);
            intent.putExtra("ViewType","storage");
            intent.putExtra("FileUri",selectedPDF.toString());
            startActivity(intent);
        }
    }
}