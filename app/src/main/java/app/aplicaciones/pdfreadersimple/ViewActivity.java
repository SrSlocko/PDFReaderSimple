package app.aplicaciones.pdfreadersimple;

import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.listener.OnRenderListener;
import com.github.barteksc.pdfviewer.listener.OnTapListener;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ViewActivity extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view);
        pdfView= findViewById(R.id.pdf_viewer);
        progressBar= findViewById(R.id.progress_bar);
        if(getIntent() !=null)
        {
            String viewType=getIntent().getStringExtra("ViewType");

            if(viewType !=null || !TextUtils.isEmpty(viewType))
                if (viewType.equals("assets")) {
                    pdfView.fromAsset("pdfExamplePrivacidad.pdf")
                            .password(null)
                            .defaultPage(0)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .onDraw(new OnDrawListener() {
                                @Override
                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                }
                            })
                            .onPageError(new OnPageErrorListener() {
                                @Override
                                public void onPageError(int page, Throwable t) {
                                    Toast.makeText(ViewActivity.this, "Error while open page" + page, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onPageChange(new OnPageChangeListener() {
                                @Override
                                public void onPageChanged(int page, int pageCount) {

                                }
                            })
                            .onTap(new OnTapListener() {
                                @Override
                                public boolean onTap(MotionEvent e) {
                                    return true;
                                }
                            })
                            .onRender(new OnRenderListener() {
                                @Override
                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                    pdfView.fitToWidth();
                                }
                            })
                            .enableAnnotationRendering(true)
                            .invalidPageColor(Color.WHITE)
                            .load();
                } else if (viewType.equals("storage")) {
                    Uri pdfFile = Uri.parse(getIntent().getStringExtra("FileUri"));

                    pdfView.fromUri(pdfFile)
                            .password(null)
                            .defaultPage(0)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .enableDoubletap(true)
                            .onDraw(new OnDrawListener() {
                                @Override
                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                }
                            })
                            .onPageError(new OnPageErrorListener() {
                                @Override
                                public void onPageError(int page, Throwable t) {
                                    Toast.makeText(ViewActivity.this, "Error while open page" + page, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onPageChange(new OnPageChangeListener() {
                                @Override
                                public void onPageChanged(int page, int pageCount) {
                                }
                            })
                            .onTap(new OnTapListener() {
                                @Override
                                public boolean onTap(MotionEvent e) {
                                    return true;
                                }
                            })
                            .onRender(new OnRenderListener() {
                                @Override
                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                    pdfView.fitToWidth();
                                }
                            })
                            .enableAnnotationRendering(true)
                            .invalidPageColor(Color.WHITE)
                            .load();
                } else if (viewType.equals("internet")) {
                    progressBar.setVisibility(View.VISIBLE);
                    FileLoader.with(this)
                            .load("url")
                            .fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC)
                            .asFile(new FileRequestListener<File>() {
                                @Override
                                public void onLoad(FileLoadRequest request, FileResponse<File> fileResponse) {
                                    progressBar.setVisibility(View.GONE);

                                    File pdfFile = fileResponse.getBody();

                                    pdfView.fromFile(pdfFile)
                                            .password(null)
                                            .defaultPage(0)
                                            .enableSwipe(true)
                                            .swipeHorizontal(false)
                                            .enableDoubletap(true)
                                            .onDraw(new OnDrawListener() {
                                                @Override
                                                public void onLayerDrawn(Canvas canvas, float pageWidth, float pageHeight, int displayedPage) {
                                                }
                                            })
                                            .onPageError(new OnPageErrorListener() {
                                                @Override
                                                public void onPageError(int page, Throwable t) {
                                                    Toast.makeText(ViewActivity.this, "Error while open page" + page, Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .onPageChange(new OnPageChangeListener() {
                                                @Override
                                                public void onPageChanged(int page, int pageCount) {

                                                }
                                            })
                                            .onTap(new OnTapListener() {
                                                @Override
                                                public boolean onTap(MotionEvent e) {
                                                    return true;
                                                }
                                            })
                                            .onRender(new OnRenderListener() {
                                                @Override
                                                public void onInitiallyRendered(int nbPages, float pageWidth, float pageHeight) {
                                                    pdfView.fitToWidth();
                                                }
                                            })
                                            .enableAnnotationRendering(true)
                                            .invalidPageColor(Color.WHITE)
                                            .load();
                                }
                                @Override
                                public void onError(FileLoadRequest request, Throwable throwable) {
                                    Toast.makeText(ViewActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                }
                            });
                }

        }

    }
}
