package com.zhengdianfang.highlightr

import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import com.zhengdianfang.highlightr.ui.HighlightTextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scrollView = ScrollView(this)
        scrollView.layoutParams = android.view.ViewGroup.LayoutParams(
            android.view.ViewGroup.LayoutParams.MATCH_PARENT,
            android.view.ViewGroup.LayoutParams.MATCH_PARENT
        )
        scrollView.isFillViewport = true
        
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 32, 32, 32)
        layout.setBackgroundColor(0xFF000000.toInt()) // Black background
        
        val highlightTextView = HighlightTextView(this)
        highlightTextView.textSize = 16f
        highlightTextView.setSource(
            """
            package com.example.demo;

            import java.util.List;
            import java.util.ArrayList;

            /**
             * A complex Java example to test highlighting
             */
            public class JavaDemo extends Thread {
                private static final int MAX_COUNT = 100;
                private volatile boolean isRunning = true;
                
                @Override
                public void run() {
                    List<String> messages = new ArrayList<>();
                    messages.add("Starting thread...");
                    
                    try {
                        for (int i = 0; i < MAX_COUNT; i++) {
                            if (!isRunning) break;
                            
                            // Check for even numbers
                            if (i % 2 == 0) {
                                System.out.println("Even: " + i);
                            } else {
                                System.out.println("Odd: " + i);
                            }
                            
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("Thread finished.");
                    }
                }
                
                public void stopThread() {
                    this.isRunning = false;
                }
                
                public static void main(String[] args) {
                    JavaDemo demo = new JavaDemo();
                    demo.start();
                    
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // Ignore
                    }
                    
                    demo.stopThread();
                }
            }
            """.trimIndent(),
            "java"
        )
        
        layout.addView(highlightTextView)
        scrollView.addView(layout)
        setContentView(scrollView)
    }
}
