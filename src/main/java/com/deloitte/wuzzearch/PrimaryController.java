package com.deloitte.wuzzearch;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class PrimaryController implements Initializable {

	@FXML
	private Label currentAddress;
	
	@FXML
	private Label rootAddress;
	
	@FXML
	private TreeView<CustomFile> treeView;
	
	@FXML
	private TextFlow textFlow;
	
	@FXML
 	private TextArea textArea;
	
	@FXML
	private Button searchBtn;
	
	@FXML
	private TextField toSearchTxt;
	
	@FXML
	private TextArea resultTextArea;
	
	@FXML
	private Button saveBtn;

	@FXML
	private TextArea fileResultArea;
	
	
	private String content;
	
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	
		
	}

	
	@FXML 
	private void onClickChoose() throws IOException{
		System.out.println("Clickedd");
		DirectoryChooser dc = new DirectoryChooser();
		File fileDirectory = dc.showDialog(null);
		if(fileDirectory!=null) {
			
			rootAddress.setText(fileDirectory.getAbsolutePath());
			
			TreeItem<CustomFile> root = new TreeItem<CustomFile>(new CustomFile(fileDirectory,fileDirectory.getName()));
			
			root.getChildren().addAll(createTree(fileDirectory.listFiles(), fileDirectory,new boolean[]{false}));
			root.setExpanded(true);
			treeView.setRoot(root);
			
			
		}
	}
	

	
	
	
	private void searchTextInFile(File file, String wordToSearch,List<String> result, List<Integer> positions) {
		// TODO Auto-generated method stub
		
		int lineNumber = 1;
		int position = 0;
		try(Scanner input = new Scanner(file)){
			
			while(input.hasNextLine()) {
				String line = (lineNumber++)+".  "+input.nextLine();
				result.add(line);
				
				if(line.indexOf(wordToSearch)!=-1) {
					positions.add(position);
		
				}
				position++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	private void showResult(List<String> result, List<Integer> positions) {
		// TODO Auto-generated method stub
		if(result.size()==0 || positions.size()==0)
			return;
		int count = 1;
		fileResultArea.clear();
		String finalResult= "======================================================================================================\t ________________________________________________________________________________________________________ \n Number of results are "+positions.size()+" \n";
		finalResult += "********  File name is "+treeView.getSelectionModel().getSelectedItem().getValue().getFile().getName()+" ********  \n";
		finalResult +="********  File Location is "+treeView.getSelectionModel().getSelectedItem().getValue().getFile().getAbsolutePath()+" ********  \n";
		for(int position: positions) {
			String toprint = "________________________________________________________________________________________________________\n\t Result "+(count++)+ "\n";
			if(position==0) {
				toprint +=result.get(position)+"\n";
				toprint +=result.get(position+1)+"\n";
			}
			else if(position==result.size()-1) {
				toprint+=result.get(position-1)+"\n";
				toprint +=result.get(position)+"\n";
			}else {
				toprint+=result.get(position-1)+"\n";
				toprint+=result.get(position)+"\n";
				toprint+=result.get(position+1)+"\n";
			}
			
			finalResult +=toprint+"\n________________________________________________________________________________________________________ \n";
			
			
			
			
		}
		finalResult +="======================================================================================================\n\n";
		fileResultArea.appendText(finalResult);
		
		
		
		content = finalResult;
		
	}


	private String getResult(List<String> result, List<Integer> positions, String filename) {
		// TODO Auto-generated method stub
		if(result.size()==0 || positions.size()==0)
			return "";
		
		TreeItem<CustomFile> item = treeView.getSelectionModel().getSelectedItem();
		if(item==null) {
			return "";
		}
		
		int count = 1;
		String finalResult= "====================================================================================================== \n ________________________________________________________________________________________________________ \n Number of results are "+positions.size()+" \n";
		finalResult += "********  File name is "+treeView.getSelectionModel().getSelectedItem().getValue().getFile().getName()+" ********  \n";
		finalResult +="********  File Location is "+treeView.getSelectionModel().getSelectedItem().getValue().getFile().getAbsolutePath()+" ********  \n";
//		String finalResult= "____________________________________________________ \n Number of results are "+positions.size()+" in "+filename+" \n";
		for(int position: positions) {
			String toprint = "\t Result "+(count++)+  " in "+filename+"\n";
			if(position==0) {
				toprint +=result.get(position)+"\n";
				toprint +=result.get(position+1)+"\n";
			}
			else if(position==result.size()-1) {
				toprint+=result.get(position-1)+"\n";
				toprint +=result.get(position)+"\n";
			}else {
				toprint+=result.get(position-1)+"\n";
				toprint+=result.get(position)+"\n";
				toprint+=result.get(position+1)+"\n";
			}
			
			finalResult +=toprint+"\n________________________________________________________________________________________________________ \n";
			
			
		}
		finalResult +="======================================================================================================\n\n";
		
		return finalResult;
		
	}

	
	@FXML
	private void handleMouseClick(MouseEvent moEvent) {
		textArea.clear();
		fileResultArea.clear();
		System.out.println("Clicked===");
		TreeItem<CustomFile> item = treeView.getSelectionModel().getSelectedItem();
		String wordToSearch = toSearchTxt.getText();
		List<String> result = new ArrayList<>();
		List<Integer> positions = new ArrayList<>();
		if(item !=null) {
			currentAddress.setText(item.getValue().getFile().getAbsolutePath());
		
		if(item.getValue().getFile().isFile() && (item.getValue().getExtension().equals("java") || item.getValue().getExtension().equals("xml"))) {
			int lineNumber = 1;
			int position = 0;
			try(Scanner input = new Scanner(item.getValue().getFile())){
				while(input.hasNextLine()) {
					String line = (lineNumber++)+".  "+input.nextLine();
					result.add(line);
					
					textArea.appendText(line+"\n");
					if(wordToSearch.length()>0 && line.indexOf(wordToSearch)!=-1) {
						
						positions.add(position);
					}else if(wordToSearch.length()==0) {
						
						fileResultArea.setText("No results");
					}
					position++;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			showResult(result, positions);
			
			
		}else {
			textArea.setText("Not a java File");
			fileResultArea.setText("Not a java File");
		}
		
		}
		
		
	}
	
	private void startSearching(File[] filesInCurrentLevel, File root, boolean[] containsFile, String[] result) {
		System.out.println("Started Searching");
		if(filesInCurrentLevel == null || filesInCurrentLevel.length <=0)
			return;
		System.out.println("Started Searching2");
		
		for(File f: filesInCurrentLevel) {
			CustomFile customFile = new CustomFile(f,f.getName());
			if(customFile.getFile().isFile()) {
				List<String> textFile = new ArrayList<>();
				List<Integer> positions = new ArrayList<>();
				
				if(customFile.getExtension().equals("java")|| customFile.getExtension().equals("xml")) {
					containsFile[0] = true;
					
					searchTextInFile(f, toSearchTxt.getText(), textFile, positions);
					result[0] +=getResult(textFile, positions,f.getName());
					
				}
				
			}else if(customFile.getFile().isDirectory()) {
				startSearching(f.listFiles(), f, containsFile, result);
			}
		}
		
	}
	
	@FXML
	

	private void onClickSearch1() throws IOException{
		TreeItem<CustomFile> item = treeView.getSelectionModel().getSelectedItem();
		String wordToSearch =  toSearchTxt.getText();
		if(wordToSearch.length()==0)
			return;
		String[] finalResult = {""};
		File root = new File(rootAddress.getText());
		boolean[] containsFile = new boolean[] {false};
		
		if(item !=null) {
			List<String> result = new ArrayList<>();
			List<Integer> positions = new ArrayList<>();
		if(item.getValue().getExtension().equals("java") || item.getValue().getExtension().equals("xml")) {
			int lineNumber = 1;
			int position = 0;
			try(Scanner input = new Scanner(item.getValue().getFile())){
				while(input.hasNextLine()) {
					String line = (lineNumber++)+".  "+input.nextLine();
					result.add(line);
					
					textArea.appendText(line+"\n");
					if(wordToSearch.length()>0 && line.indexOf(wordToSearch)!=-1) {
						
						positions.add(position);
					}else if(wordToSearch.length()==0) {
						
						fileResultArea.setText("No results");
					}
					position++;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			showResult(result, positions);
			
			
		}else {
			textArea.setText("Not a java File");
			fileResultArea.setText("Not a java File");
		}
		
		}
		
		startSearching(root.listFiles(), root, containsFile, finalResult);
		resultTextArea.clear();
		resultTextArea.setText(finalResult[0]);
	}
	
	
	

	private List<TreeItem<CustomFile>> createTree(File[] filesInCurrentLevel, File root, boolean[] containsFile) {
		
		if(filesInCurrentLevel==null || filesInCurrentLevel.length <=0)
			return new ArrayList<>();
		
		
		List<TreeItem<CustomFile>> treeItemListInCurrentLevel = new ArrayList<>();
		
		
		for(File f: filesInCurrentLevel) {
			if(f.isFile()) {
				
				String fileName  = f.getName();
				String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1, f.getName().length());
				System.out.println(fileExtension);
				
				if(fileExtension.equals("java") || fileExtension.equals("xml")) {
					containsFile[0] = true;
					treeItemListInCurrentLevel.add(new TreeItem<CustomFile>(new CustomFile(f,f.getName()), new ImageView(new Image(ClassLoader.getSystemResourceAsStream("file16x16.png")))));
				}
				
			}else if(f.isDirectory()) {
				TreeItem<CustomFile> treeItem = new TreeItem<CustomFile>(new CustomFile(f,f.getName()),new ImageView(new Image(ClassLoader.getSystemResourceAsStream("folder16.png"))));
				treeItem.setExpanded(true);
				List<TreeItem<CustomFile>> subList = createTree(f.listFiles(), root, containsFile);
//				if(containsFile[0]) {
					treeItem.getChildren().addAll(subList);
					treeItemListInCurrentLevel.add(treeItem);
//				}
				
				
			}
		}
		
		return treeItemListInCurrentLevel;
		
	}
	
	
	
	@FXML
	private void onClickSaveFile() throws IOException{
		if(rootAddress.getText() ==null)
			return;
		TreeItem<CustomFile> item = treeView.getSelectionModel().getSelectedItem();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.txt*"));
		
		fileChooser.setInitialFileName(new File(rootAddress.getText()).getName());
		File file =fileChooser.showSaveDialog(null);
		
		if(file!=null) {
			saveTextToFile(file);
		}
		
	}

	private void saveTextToFile(File file) {
		// TODO Auto-generated method stub
		if (resultTextArea.getText().length()<=0) {
			return;
		}
		try {
			PrintWriter writer;
			writer = new PrintWriter(file);
			writer.println(resultTextArea.getText()+".txt");
			writer.close();
		}catch (IOException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
	}
	
	
	 
	
	
	
	
}
