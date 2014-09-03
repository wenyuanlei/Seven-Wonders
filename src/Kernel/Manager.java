package Kernel;

import GUI.GUIManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Lane
 * ��Ϸ������-��Ϸ��ʼ�����
 */

public class Manager extends Application{
	
	public GUIManager gm = new GUIManager();
	
	public static void main(String[] args){
		Manager.launch(args);
	}
	
	public void start(Stage stage){
		gm.initScene();
		gm.initStage();
		stage = gm.getStage();
		stage.show();
	}
}