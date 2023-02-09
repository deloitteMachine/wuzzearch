package com.deloitte.wuzzearch;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import javafx.scene.control.TextArea;

public class WordHighlight  extends JFrame{
	class MyHighLighterPainter extends DefaultHighlightPainter{

		public MyHighLighterPainter(Color c) {
			super(c);
			// TODO Auto-generated constructor stub
		}
		
	}

}
