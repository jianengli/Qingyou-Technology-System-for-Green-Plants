package qingyouSystem;
 import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.text.StyleConstants;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 public class LoginInterface extends Application {
	// 其他类需要调用的组件与变量
	static int countInGamePart = 0;// 植物生活条件的总评分
	static String strSex = null;
	static int indexOfBabyFigure2 = 0;// 促进虚拟植物成长时，用来辨别植物年龄
	static TextField userTextField = new TextField();
	static String nameOfBaby = "";
	static String content2 = "";// 人物花园游戏界面回话内容
	private JFrame jf;
	private ChatServer cserver;// 服务器对象
	static TableView<Person> table = new TableView<Person>();
	static TableColumn<Person, String> table_onlineUser = new TableColumn<Person, String>("在线用户");// 在线用户表
	static ArrayList<String> listUsers = new ArrayList<>();
	static ObservableList<Person> utm = FXCollections.observableArrayList();
	static int selectedRow = 0;
	// private JTextField jta_msg;// 发送消息输入框
	boolean isExistRoom = false;
	static TextArea area1 = new TextArea();
	static TextArea area2 = new TextArea();
	static OutputStream ops;
	// 传给服务端的内容
	static String sendMsg2;
	private TextField tfRoom = new TextField();
	private Button btBack = new Button("创建房间");
	private Button btEnter = new Button("加入房间");
	static ConnectSql outerClass = new ConnectSql();
	static WebCrawler crawlerObejct3 = new WebCrawler();
	static Text tCrawl = new Text();
	static VBox pWC = new VBox();
	final static WebView browser = new WebView();
	final static WebEngine webEngine = browser.getEngine();
	static VBox webBrowser = new VBox();
	// 报错对话框
	static Alert error = new Alert(Alert.AlertType.ERROR, "好可惜，您还未领养宠物呢~");
 	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
 	// RegistrationModule rm=new RegistrationModule();
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		BorderPane p = new BorderPane();
		ImageView image = new ImageView(("image/background2.jpg"));
		StackPane p1 = new StackPane();
		p1.setPadding(new Insets(25, 25, 5, 25));
		ImageView logo = new ImageView(new Image("image/logo.png"));
		logo.setFitWidth(275 );
		logo.setFitHeight(140);
		p1.getChildren().add(logo);
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		Text scenetitle = new Text("用户登陆");
		scenetitle.setFill(Color.WHITE);
		scenetitle.setFont(Font.font("YouYuan", FontWeight.BOLD, 40));
		grid.add(scenetitle, 0, 0, 2, 1);
		Label phonenumber = new Label("手机号码:");
		phonenumber.setTextFill(Color.WHITE);
		phonenumber.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
		grid.add(phonenumber, 0, 1);
		userTextField.setFont(Font.font("KaiTi", FontWeight.NORMAL, 20));
		grid.add(userTextField, 1, 1);
		Label pw = new Label("密码:");
		pw.setTextFill(Color.WHITE);
		pw.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
		grid.add(pw, 0, 2);
		PasswordField pwBox = new PasswordField();
		pwBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(pwBox, 1, 2);
		Button btn = new Button("         登  陆         ");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String phoneNumber = userTextField.getText();
				String pw = pwBox.getText();
				if (outerClass.hasRegiste(phoneNumber, pw) == true) {
					System.out.println("登陆成功，enjoy yourself！");// 主页面
					Stage mainStage = new Stage();
					BorderPane pm = new BorderPane();
					//主页面图片
					ImageView imagesB = new ImageView(
							"image/park.jpg");
//					imagesB.setFitHeight(250);
//					imagesB.setFitWidth(300);
					imagesB.fitWidthProperty().bind(pm.widthProperty());
					imagesB.fitHeightProperty().bind(pm.heightProperty());
					pm.getChildren().add(imagesB);
//					lbSP.setGraphic(iSP);
					MenuBar menuBar = new MenuBar();
					// menuBar
					menuBar.prefWidthProperty().bind(mainStage.widthProperty());
					pm.setTop(menuBar);
					Menu secretPark = new Menu("人物花园", new ImageView("image/secretPark.png"));
					MenuItem openSecretPark = new MenuItem("查询植物状态");
					MenuItem gamePart = new MenuItem("进入养成类游戏板块");
					secretPark.getItems().addAll(openSecretPark, gamePart);
					Menu worldPark = new Menu("世界花园", new ImageView("image/worldPark.png"));
					MenuItem openWorldPark = new MenuItem("进入");
					worldPark.getItems().add(openWorldPark);
					Menu communityPark = new Menu("社区花园", new ImageView("image/communityPark.png"));
					MenuItem openCommunityPark = new MenuItem("进入");
					communityPark.getItems().add(openCommunityPark);
					Menu mall = new Menu("商城", new ImageView("image/mall.png"));
					Menu myCenter = new Menu("个人中心", new ImageView("image/myCenter.png"));
					MenuItem openMyCenter = new MenuItem("进入");
					myCenter.getItems().add(openMyCenter);
					menuBar.getMenus().addAll(secretPark, worldPark, communityPark, mall, myCenter);
					// 每一个模块如下
					// 秘密花园
					openSecretPark.setOnAction(actionEvent -> {
						// 数据库在此插入
						int age = (int) (1 + Math.random() * (18 - 1 + 1));
						int indexOfBabyFigure;
						// 确定年龄
						if (age <= 6)
							indexOfBabyFigure = 1;
						else
							indexOfBabyFigure = 2;
						DecimalFormat df = new DecimalFormat("######0.00");
						double temperature = Double.parseDouble(df.format((double) (1 + Math.random() * 33 + 4)));
						double light = Double.parseDouble(df.format((double) (1 + Math.random() * 81 + 20)));
						double humidity = Double.parseDouble(df.format((double) (1 + Math.random() * 51 + 50)));
						int count = 0;// 植物生活条件的总评分
						countInGamePart = count;
						// 确定温度，湿度与光照
						try {
							nameOfBaby = outerClass.getBabyName(outerClass.getPersonalInfo(phoneNumber).getString(4))
									.getString(1);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						String talk = nameOfBaby + "说：" + System.getProperty("line.separator");
						double temperatureGrade;
						if (temperature <= 18) {
							temperatureGrade = Double
									.parseDouble(df.format((temperature * (60 - 20) / (18 - 4) + 8.57)));
							talk += "好冷啊，我快被冻死啦。。" + System.getProperty("line.separator");
							count += 1;
						} else if (temperature <= 27 && temperature > 18) {
							temperatureGrade = Double
									.parseDouble(df.format((temperature * (80 - 60) / (27 - 18) + 15)));
							talk += "这天气真舒服！好想去森林玩呀！" + System.getProperty("line.separator");
							count += 3;
						} else {
							temperatureGrade = Double
									.parseDouble(df.format((temperature * (100 - 80) / (36 - 27) + 12.5)));
							talk += "好热啊，受不了啦！" + System.getProperty("line.separator");
							count += 2;
						}
						;
						double humidityGrade;
						if (humidity <= 65) {
							humidityGrade = Double.parseDouble(df.format((humidity * 40 / (65 - 50) - 113.33) + 12.5));
							talk += "我好渴，好像喝水啊。。" + System.getProperty("line.separator");
							count += 1;
						} else if (humidity <= 82 && humidity > 65) {
							humidityGrade = Double.parseDouble(df.format(humidity * (80 - 60) / (82 - 65) - 11.47));
							talk += "哇，喝饱啦，好开心呀！" + System.getProperty("line.separator");
							count += 3;
						} else {
							humidityGrade = Double.parseDouble(df.format(humidity * (100 - 80) / (100 - 82) - 11.11));
							talk += "不行啊，我不会游泳啊，好难受呀！" + System.getProperty("line.separator");
							count += 2;
						}
						double lightGrade = Double.parseDouble(df.format(light));
						if (light <= 60) {
							talk += "天气不好，我还是宅在家中看电视或玩电脑吧~";
							count += 1;
						} else {
							talk += "今天天气太好啦！我出去玩啦~";
							count += 3;
						}
						System.out.print(count);
						try {
							outerClass.storeBabyInfo(
									outerClass.getBabyName(outerClass.getPersonalInfo(phoneNumber).getString(4))
											.getString(1),
									String.valueOf(age), String.valueOf(temperature), String.valueOf(light),
									String.valueOf(humidity),
									outerClass.getBabyName(outerClass.getPersonalInfo(phoneNumber).getString(4))
											.getString(2));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						pm.getChildren().clear();
						pm.setTop(menuBar);
						// BorderPane pSP=new BorderPane();
						MenuBar menuBarSP = new MenuBar();
						menuBarSP.prefHeightProperty().bind(mainStage.heightProperty());
						// menuBarSP.wid
						Menu wSP1 = new Menu("植物状态");
						// Menu wSP2 = new Menu("进入养成类游戏板块");
						StackPane mainP=new StackPane();
						
						HBox plbSp = new HBox();
						
						Label lbSP = new Label();
						lbSP.setContentDisplay(ContentDisplay.LEFT);
//						lbSP.setPadding(new Insets(0,130,20,0));
						
//						lbSP.setWrapText(true);
//						lbSP.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						lbSP.setGraphicTextGap(20);
						
						//对话框
						StackPane dialog2 = new StackPane();
						Rectangle outside = new Rectangle(380, 150);
						outside.setFill(Color.LIGHTBLUE);
						Rectangle inside = new Rectangle(365, 135);
						inside.setFill(Color.WHITE);
						final Text talkText = new Text(talk);// 对话内容
						talkText.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						talkText.setFont(Font.font(20));
						talkText.setWrappingWidth(350);
						
						dialog2.getChildren().addAll(outside, inside, talkText);
						
						
						// 家里背景
						ImageView homeB = new ImageView("image/park1.png");
						homeB.setFitHeight(200);
						homeB.setFitWidth(200);
						homeB.fitWidthProperty().bind(mainP.widthProperty());
						homeB.fitHeightProperty().bind(mainP.heightProperty());
						
						mainP.getChildren().addAll(homeB,plbSp);
						plbSp.getChildren().addAll(lbSP, dialog2);
						menuBarSP.getMenus().add(wSP1);
						GridPane gSP = new GridPane();
						gSP.setPadding(new Insets(10, 25, 10, 25));
						gSP.setAlignment(Pos.CENTER);
						gSP.setHgap(40);
						gSP.setVgap(25);
						Label tpTitle = new Label("温度 " + String.valueOf(temperature) + "°C");
						tpTitle.setContentDisplay(ContentDisplay.TOP);
						tpTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						gSP.add(tpTitle, 0, 1);
						StackPane p1 = new StackPane();
						Text t1 = new Text(String.valueOf(temperatureGrade) + "分");
						t1.setFont(Font.font("YouYuan", FontWeight.BOLD, 17));
						Circle c1 = new Circle(50);
						Circle c1i = new Circle(40);
						c1i.setFill(Color.WHITE);
						Circle c1run = new Circle(0, 0, 5);
						Arc a1 = new Arc(c1.getCenterX(), c1.getCenterY(), 40, 40, 90, -3.6 * temperatureGrade);
						PathTransition pt = new PathTransition();
						pt.setDuration(javafx.util.Duration.millis(2000));
						pt.setPath(a1);
						pt.setNode(c1run);
						pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
						pt.setAutoReverse(false);
						pt.play();
						p1.getChildren().addAll(a1, c1, c1i, t1, c1run);
						tpTitle.setGraphic(p1);
						Label humTitle = new Label("湿度 " + String.valueOf(humidity) + "%");
						humTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						gSP.add(humTitle, 1, 1);
						humTitle.setContentDisplay(ContentDisplay.TOP);
						StackPane p2 = new StackPane();
						Text t2 = new Text(String.valueOf(humidityGrade) + "分");
						t2.setFont(Font.font("YouYuan", FontWeight.BOLD, 17));
						Circle c2 = new Circle(50);
						Circle c2i = new Circle(40);
						c2i.setFill(Color.WHITE);
						Circle c2run = new Circle(0, 0, 5);
						Arc a2 = new Arc(c2.getCenterX(), c2.getCenterY(), 40, 40, 90, -3.6 * humidityGrade);
						PathTransition pt2 = new PathTransition();
						pt2.setDuration(javafx.util.Duration.millis(2000));
						pt2.setPath(a2);
						pt2.setNode(c2run);
						pt2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
						pt2.setAutoReverse(false);
						pt2.play();
						p2.getChildren().addAll(a2, c2, c2i, t2, c2run);
						humTitle.setGraphic(p2);
						Label lTitle = new Label("光照 " + String.valueOf(light) + "%");
						lTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						gSP.add(lTitle, 2, 1);
						lTitle.setContentDisplay(ContentDisplay.TOP);
						StackPane p3 = new StackPane();
						Text t3 = new Text(String.valueOf(lightGrade) + "分");
						t3.setFont(Font.font("YouYuan", FontWeight.BOLD, 17));
						Circle c3 = new Circle(50);
						Circle c3i = new Circle(40);
						c3i.setFill(Color.WHITE);
						Circle c3run = new Circle(0, 0, 5);
						Arc a3 = new Arc(c3.getCenterX(), c3.getCenterY(), 40, 40, 90, -3.6 * lightGrade);
						PathTransition pt3 = new PathTransition();
						pt3.setDuration(javafx.util.Duration.millis(2000));
						pt3.setPath(a3);
						pt3.setNode(c3run);
						pt3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
						pt3.setAutoReverse(false);
						pt3.play();
						p3.getChildren().addAll(a3, c3, c3i, t3, c3run);
						lTitle.setGraphic(p3);
						try {
							strSex = outerClass.getPersonalInfo(phoneNumber).getString(5);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (strSex.equals("男性")) {
							menuBarSP.setStyle("-fx-base: orange");
							c1.setFill(Color.LIGHTGREEN);
							c2.setFill(Color.LIGHTGREEN);
							c3.setFill(Color.LIGHTGREEN);
							c1run.setFill(Color.GREEN);
							c2run.setFill(Color.GREEN);
							c3run.setFill(Color.GREEN);
							if (indexOfBabyFigure == 1) {
								ImageView iSP = new ImageView(
										"image/maleFigure" + String.valueOf(indexOfBabyFigure) + ".png");
								
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else if (count <= 3) {
								ImageView iSP = new ImageView(
										"image/maleFigure" + String.valueOf(indexOfBabyFigure) + "bad.png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else if (count <= 6 && count > 3) {
								ImageView iSP = new ImageView(
										"image/maleFigure" + String.valueOf(indexOfBabyFigure) + "bad.png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else {
								ImageView iSP = new ImageView(
										"image/maleFigure" + String.valueOf(indexOfBabyFigure) + "happy.png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							}
						} else if (strSex.equals("女性")) {
							menuBarSP.setStyle("-fx-base: green");
							c1.setFill(Color.LIGHTGOLDENRODYELLOW);
							c2.setFill(Color.LIGHTGOLDENRODYELLOW);
							c3.setFill(Color.LIGHTGOLDENRODYELLOW);
							c1run.setFill(Color.ORANGE);
							c2run.setFill(Color.ORANGE);
							c3run.setFill(Color.ORANGE);
							if (indexOfBabyFigure == 1) {
								ImageView iSP = new ImageView(
										"image/femaleFigure" + String.valueOf(indexOfBabyFigure) + ".png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else if (count <= 3) {
								ImageView iSP = new ImageView(
										"image/femaleFigure" + String.valueOf(indexOfBabyFigure) + "bad.png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else if (count <= 6 && count > 3) {
								ImageView iSP = new ImageView(
										"image/femaleFigure" + String.valueOf(indexOfBabyFigure) + ".png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							} else {
								ImageView iSP = new ImageView(
										"image/femaleFigure" + String.valueOf(indexOfBabyFigure) + "happy.png");
								iSP.setFitHeight(250);
								iSP.setFitWidth(300);
								lbSP.setGraphic(iSP);
							}
						}
						// 养成类游戏板块
						gamePart.setOnAction(e -> {
							Stage gameStage = new Stage();
							BorderPane p = new BorderPane();
							ImageView image = new ImageView(("image/GameBackground.png"));
							image.fitWidthProperty().bind(p.widthProperty());
							image.fitHeightProperty().bind(p.heightProperty());
							p.getChildren().add(image);
							HBox header = new HBox();
							header.setPadding(new Insets(30, 30, 30, 30));
							Text place = new Text("花园");
							place.setFont(Font.font("YouYuan", FontWeight.BOLD, 30));
							place.setFill(Color.WHITE);
							Button btEnhanceGrowth = new Button("促进成长");
							HBox.setMargin(btEnhanceGrowth, new Insets(20, 20, 20, 50));
							HBox.setMargin(place, new Insets(20, 20, 20, 0));
							header.getChildren().addAll(place, btEnhanceGrowth);
							HBox body = new HBox();
							body.setAlignment(Pos.CENTER);
							body.setMaxHeight(300);
							Label middle = new Label();
							if (strSex.equals("男性")) {
								ImageView iSP2 = new ImageView("image/maleFigure" + String.valueOf(1) + ".png");
								iSP2.setFitHeight(250);
								iSP2.setFitWidth(300);
								middle.setGraphic(iSP2);
							} else if (strSex.equals("女性")) {
								ImageView iSP2 = new ImageView("image/femaleFigure" + String.valueOf(1) + ".png");
								iSP2.setFitHeight(250);
								iSP2.setFitWidth(300);
								middle.setGraphic(iSP2);
							}
							;
							Timeline timeline = new Timeline();
							// int indexOfBabyFigure2=0;//促进虚拟植物成长时，用来辨别植物年龄
							for (int countSec = 0; countSec <= 1; countSec++) {
								indexOfBabyFigure2++;
								// final String text =
								// Integer.toString(countSec);
								if (countSec == 1) {
									KeyFrame frame = new KeyFrame(javafx.util.Duration.seconds(countSec * 5),
											event2 -> {
												// 五秒成长到青年
												// body.getChildren().removeAll(middle);
												// Label middle2=new Label();
												// body.getChildren().add(middle2);
												middle.setGraphic(null);
												// Text t=new Text(text);
												// body.getChildren().add(t);
												if (strSex.equals("男性")) {
													if (indexOfBabyFigure2 == 1) {
														ImageView iSP2 = new ImageView(
																"image/maleFigure" + String.valueOf(2) + ".png");
														iSP2.setFitHeight(250);
														iSP2.setFitWidth(300);
														middle.setGraphic(iSP2);
													} else {
														if (countInGamePart <= 3) {
															ImageView iSP2 = new ImageView(
																	"image/maleFigure" + String.valueOf(2) + "bad.png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														} else if (countInGamePart <= 6 && countInGamePart > 3) {
															ImageView iSP2 = new ImageView(
																	"image/maleFigure" + String.valueOf(2) + ".png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														} else {
															ImageView iSP2 = new ImageView("image/maleFigure"
																	+ String.valueOf(2) + "happy.png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														}
													}
												} else if (strSex.equals("女性")) {
													if (indexOfBabyFigure2 == 1) {
														ImageView iSP2 = new ImageView(
																"image/femaleFigure" + String.valueOf(2) + ".png");
														iSP2.setFitHeight(250);
														iSP2.setFitWidth(300);
														middle.setGraphic(iSP2);
													} else {
														if (countInGamePart <= 3) {
															ImageView iSP2 = new ImageView("image/femaleFigure"
																	+ String.valueOf(2) + "bad.png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														} else if (countInGamePart <= 6 && countInGamePart > 3) {
															ImageView iSP2 = new ImageView(
																	"image/femaleFigure" + String.valueOf(2) + ".png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														} else {
															ImageView iSP2 = new ImageView("image/femaleFigure"
																	+ String.valueOf(2) + "happy.png");
															iSP2.setFitHeight(250);
															iSP2.setFitWidth(300);
															middle.setGraphic(iSP2);
														}
													}
												}
											});
									timeline.getKeyFrames().add(frame);
								}
							}
							btEnhanceGrowth.setOnAction(event3 -> {
								timeline.play();
								// header.getChildren().removeAll(progressBarGrowth);
								ProgressBar progressBarGrowth = new ProgressBar();// 成长条
								progressBarGrowth.setPrefSize(300, 1);
								header.getChildren().add(progressBarGrowth);
								HBox.setMargin(progressBarGrowth, new Insets(22.5, 20, 20, 50));
							});
							FlowPane dialogPart = new FlowPane(400, 20);// 对话框
							body.setMargin(dialogPart, new Insets(30, 30, 30, 30));
							StackPane dialog = new StackPane();
							Rectangle layout1 = new Rectangle(380, 150);
							layout1.setFill(Color.LIGHTBLUE);
							Rectangle layout2 = new Rectangle(365, 135);
							layout2.setFill(Color.WHITE);
							final Text displayedText = new Text();// 对话内容
							displayedText.setFont(Font.font(20));
							displayedText.setWrappingWidth(350);
							ToggleGroup group = new ToggleGroup();
							HBox RadioButtonPane = new HBox();
							RadioButtonPane.setMaxHeight(30);
							dialogPart.setMargin(RadioButtonPane, new Insets(0, 30, 0, 30));
							RadioButtonPane.setSpacing(20);
							RadioButton radioA = new RadioButton("游戏机");
							RadioButton radioB = new RadioButton("网球拍");
							RadioButton radioC = new RadioButton("笔记本");
							RadioButton radioD = new RadioButton("泥巴");
							radioA.setToggleGroup(group);
							radioB.setToggleGroup(group);
							radioC.setToggleGroup(group);
							radioD.setToggleGroup(group);
							RadioButtonPane.getChildren().addAll(radioA, radioB, radioC, radioD);
							dialog.getChildren().addAll(layout1, layout2, displayedText);
							body.getChildren().addAll(middle, dialogPart);
							FlowPane bottom = new FlowPane();
							bottom.setAlignment(Pos.CENTER);
							bottom.setPadding(new Insets(30, 30, 30, 30));
							final Label label = new Label("亲密度:");
							FlowPane.setMargin(label, new Insets(30, 20, 30, 30));
							label.setFont(Font.font("YouYuan", FontWeight.BOLD, 20));
							label.setTextFill(Color.BLACK);
							ProgressBar progressBar = new ProgressBar(0.5F);
							progressBar.setPrefSize(500, 1.5);
							Button chatWithBaby = new Button("与" + nameOfBaby + "对话");// 与植物聊天
							FlowPane.setMargin(chatWithBaby, new Insets(30, 30, 30, 30));
							Timeline timeline2 = new Timeline();// 计时器2
							String content = " 主人,我要去外面晒晒太阳啦~你希望带什么陪我一起去玩呢？";
							IntegerProperty charCount = new SimpleIntegerProperty();// 第一次对话
							KeyFrame startFrame = new KeyFrame(javafx.util.Duration.ZERO, new KeyValue(charCount, 0));
							KeyFrame endFrame = new KeyFrame(javafx.util.Duration.seconds(2),
									new KeyValue(charCount, content.length()));
							KeyFrame checkboxframe = new KeyFrame(javafx.util.Duration.seconds(2.5), event5 -> {
								dialogPart.getChildren().add(RadioButtonPane);
							});
							timeline2.getKeyFrames().addAll(startFrame, endFrame, checkboxframe);
							Timeline timeline3 = new Timeline();// 计时器3
							// String content=" 主人,我要去外面晒晒太阳啦~你希望带什么陪我一起去玩呢？";
							IntegerProperty charCount2 = new SimpleIntegerProperty();// 第一次对话
							// 选项对应结果
							radioA.setOnAction(e2 -> {
								if (radioA.isSelected()) {
									displayedText.setText(null);
									content2 = " 哎呀，不用了，你真是一点都不懂人家~";
									KeyFrame startFrame2 = new KeyFrame(javafx.util.Duration.ZERO,
											new KeyValue(charCount2, 0));
									KeyFrame endFrame2 = new KeyFrame(javafx.util.Duration.seconds(1),
											new KeyValue(charCount2, content2.length()));
									KeyFrame progressBarChange = new KeyFrame(javafx.util.Duration.seconds(1),
											event5 -> {
												progressBar.setProgress(progressBar.getProgress() - 0.1F);
											});
									timeline3.getKeyFrames().addAll(startFrame2, endFrame2, progressBarChange);
									timeline3.playFromStart();
								}
							});
							radioB.setOnAction(e3 -> {
								if (radioB.isSelected()) {
									displayedText.setText(null);
									content2 = " ummm...今天不想去运动啊~";
									KeyFrame startFrame2 = new KeyFrame(javafx.util.Duration.ZERO,
											new KeyValue(charCount2, 0));
									KeyFrame endFrame2 = new KeyFrame(javafx.util.Duration.seconds(1),
											new KeyValue(charCount2, content2.length()));
									KeyFrame progressBarChange = new KeyFrame(javafx.util.Duration.seconds(1),
											event5 -> {
												progressBar.setProgress(progressBar.getProgress() - 0.1F);
											});
									timeline3.getKeyFrames().addAll(startFrame2, endFrame2, progressBarChange);
									timeline3.playFromStart();
								}
							});
							radioC.setOnAction(e4 -> {
								if (radioC.isSelected()) {
									displayedText.setText(null);
									content2 = " 笔记本是神马? 能吃吗??";
									KeyFrame startFrame2 = new KeyFrame(javafx.util.Duration.ZERO,
											new KeyValue(charCount2, 0));
									KeyFrame endFrame2 = new KeyFrame(javafx.util.Duration.seconds(1),
											new KeyValue(charCount2, content2.length()));
									KeyFrame progressBarChange = new KeyFrame(javafx.util.Duration.seconds(1),
											event5 -> {
												progressBar.setProgress(progressBar.getProgress() - 0.1F);
											});
									timeline3.getKeyFrames().addAll(startFrame2, endFrame2, progressBarChange);
									timeline3.playFromStart();
								}
							});
							radioD.setOnAction(e5 -> {
								if (radioD.isSelected()) {
									displayedText.setText(null);
									content2 = " 哈哈，主人对我真好！";
									KeyFrame startFrame2 = new KeyFrame(javafx.util.Duration.ZERO,
											new KeyValue(charCount2, 0));
									KeyFrame endFrame2 = new KeyFrame(javafx.util.Duration.seconds(1),
											new KeyValue(charCount2, content2.length()));
									KeyFrame progressBarChange = new KeyFrame(javafx.util.Duration.seconds(1),
											event5 -> {
												progressBar.setProgress(progressBar.getProgress() + 0.1F);
											});
									timeline3.getKeyFrames().addAll(startFrame2, endFrame2, progressBarChange);
									timeline3.playFromStart();
								}
							});
							chatWithBaby.setOnAction(event4 -> {
								dialogPart.getChildren().add(dialog);
								timeline2.playFromStart();
							});
							charCount.addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> observable, Number oldValue,
										Number newValue) {
									String textToDisplay = content.substring(0, newValue.intValue());
									displayedText.setText(textToDisplay);
								}
							});
							charCount2.addListener(new ChangeListener<Number>() {
								@Override
								public void changed(ObservableValue<? extends Number> observable, Number oldValue,
										Number newValue) {
									String textToDisplay = content2.substring(0, newValue.intValue());
									displayedText.setText(textToDisplay);
								}
							});
							bottom.getChildren().addAll(label, progressBar, chatWithBaby);
							p.setTop(header);
							p.setCenter(body);
							p.setBottom(bottom);
							Scene scene = new Scene(p, 900, 520);
							gameStage.setTitle("游戏页面");
							gameStage.setScene(scene);
							gameStage.show();
						});
						gSP.setStyle("-fx-border-color: green");
						pm.setLeft(menuBarSP);
						pm.setCenter(mainP);
						pm.setBottom(gSP);
						// pSP.setTop(menuBarSP);
						// pm.setCenter(pSP);
					});
					// worldPark
					openWorldPark.setOnAction(actionEvent -> {
						pm.getChildren().clear();
						
						ImageView worldB = new ImageView(
								"image/personalBackground.jpg");
//						imagesB.setFitHeight(250);
//						imagesB.setFitWidth(300);
						worldB.fitWidthProperty().bind(pm.widthProperty());
						worldB.fitHeightProperty().bind(pm.heightProperty());
						pm.getChildren().add(worldB);
						
						pm.setTop(menuBar);
						pWC.getChildren().clear();
						webBrowser.getChildren().clear();
						VBox vWpMain = new VBox(10);
						vWpMain.setAlignment(Pos.TOP_CENTER);
						GridPane vWP = new GridPane();
						vWP.setStyle("-fx-border-color: green");
						vWP.setPadding(new Insets(25, 25, 25, 25));
						vWP.setAlignment(Pos.TOP_CENTER);
						vWP.setHgap(10);
						vWP.setVgap(10);
						Label htmlTitle = new Label("起始网站");
						htmlTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						vWP.add(htmlTitle, 0, 1);
						TextField tHtmlTitle = new TextField();
						vWP.add(tHtmlTitle, 1, 1);
						Label searchTitle = new Label("查找信息");
						searchTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						vWP.add(searchTitle, 0, 2);
						TextField tSearchTitle = new TextField();
						vWP.add(tSearchTitle, 1, 2);
						webBrowser.setPrefWidth(800);
						webBrowser.setPrefHeight(800);
						webBrowser.setStyle("-fx-border-color: green");
						pWC.setPrefWidth(400);
						pWC.setPrefHeight(400);
						vWpMain.getChildren().addAll(vWP, pWC);
						// StackPane pWC = new StackPane();
						pWC.setAlignment(Pos.TOP_CENTER);
						pWC.setStyle("-fx-border-color: green");
						// Text tCrawl = new Text();
						pWC.getChildren().add(tCrawl);
						Button btnS = new Button("查找植物养殖心得");
						vWP.add(btnS, 1, 4);// crawlerObejct
						btnS.setOnAction(actionEvent2 -> {
							crawlerObejct3.crawler(tHtmlTitle.getText(), tSearchTitle.getText());
						});
						pm.setLeft(vWpMain);
						pm.setCenter(webBrowser);
						// pm.setBottom(vbn);
					});
					openCommunityPark.setOnAction(actionEvent -> {
						pm.getChildren().clear();
						pm.getChildren().remove(btBack);
						pm.getChildren().remove(btEnter);
						pm.getChildren().remove(sendMsg2);
						pm.getChildren().remove(userTextField);
						pm.getChildren().remove(table_onlineUser);
						pm.getChildren().remove(table);
						pm.getChildren().remove(area2);
						pm.getChildren().remove(area1);
						pm.getChildren().remove(tfRoom);
						pm.setTop(menuBar);
						FlowPane pSUI = new FlowPane();
						pSUI.setPrefWidth(250);
						pSUI.setPadding(new Insets(35, 25, 35, 25));
						pSUI.setStyle("-fx-border-color: green");
						pSUI.setHgap(10);
						pSUI.setVgap(20);
						Button btQuit = new Button("退出房间");
						table.getColumns().clear();
						table_onlineUser.setPrefWidth(260);
						table.getColumns().add(table_onlineUser);
						table.setPrefWidth(280);
						table.setPrefHeight(370);
						table_onlineUser.setCellValueFactory(new PropertyValueFactory<Person, String>("userName"));
						table.setItems(utm);
						table.setPlaceholder(new Button("當前沒有用戶"));
						table.setTableMenuButtonVisible(true);// 选择隐藏某列
						table.getFocusModel().getFocusedIndex();
						Text tRoom = new Text("房间号：");
						tfRoom.setOnKeyPressed(e -> {
							KeyCode keyChar = (e.getCode());
							if (keyChar == KeyCode.DIGIT0 && keyChar == KeyCode.DIGIT9 && keyChar == KeyCode.DIGIT1
									&& keyChar == KeyCode.DIGIT2 && keyChar == KeyCode.DIGIT3
									&& keyChar == KeyCode.DIGIT4 && keyChar == KeyCode.DIGIT5
									&& keyChar == KeyCode.DIGIT6 && keyChar == KeyCode.DIGIT7
									&& keyChar == KeyCode.DIGIT8) {
							} else {
								e.consume();// 屏蔽掉非法输入
							}
						});
						btBack.setOnAction(e -> {// 创建房间
							table.getItems().clear();
							listUsers.clear();
							outerClass.onlineMarkBackToZero();
							try {
								actionServer();// 房主启动聊天服务器
							} catch (IOException | SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							if (isExistRoom == false) {
								MyClient mc = new MyClient();
								sendMsg2 = "";
								mc.initClient("127.0.0.1", Integer.parseInt(tfRoom.getText()));
								// 作为客户端，启动socket连接服务器
								try {
									outerClass.onlineMark(ChatTools.user.getName());
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								outerClass.getOnlineUsersName(listUsers);
								isExistRoom = true;
							} else
								isExistRoom = false;
							table_onlineUser.setText("在线用户(" + outerClass.countOnlineUsers() + ")");
							for (int i = 0; i < listUsers.size(); i++) {
								utm.add(new Person(listUsers.get(i)));
							}
							table.setItems(utm);
							table.refresh();
						});
						btEnter.setOnAction(e -> {
							table.getItems().clear();
							listUsers.clear();
							try {
								MyClient mc2 = new MyClient();
								sendMsg2 = "";
								mc2.initClient("127.0.0.1", Integer.parseInt(tfRoom.getText()));
								outerClass.onlineMark(ChatTools.user.getName());
								table_onlineUser.setText("在线用户(" + outerClass.countOnlineUsers() + ")");
								outerClass.getOnlineUsersName(listUsers);
								for (int i = 0; i < listUsers.size(); i++) {
									utm.add(new Person(listUsers.get(i)));
								}
								table.setItems(utm);
								table.refresh();
							} catch (NumberFormatException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						});
						table.setOnMouseClicked(e -> {
							table.getItems().clear();
							listUsers.clear();
							table_onlineUser.setText("在线用户(" + outerClass.countOnlineUsers() + ")");
							outerClass.getOnlineUsersName(listUsers);
							for (int i = 0; i < listUsers.size(); i++) {
								utm.add(new Person(listUsers.get(i)));
							}
							table.setItems(utm);
							table.refresh();
						});
						// ObservableList<ServerThread> utm =
						// FXCollections.observableArrayList(ChatTools.stList);
						// UserInfoTableModel utm = new UserInfoTableModel(sts);
						// table.setItems(utm);
						ContextMenu pop = getTablePop();
						table.setContextMenu(pop);
						pSUI.setVisible(true);
						// pSUI.setDefaultCloseOperation(3);// 界面关闭时，程序退出
						btQuit.setOnAction(e -> {// 退出房间
							table.getItems().clear();
							listUsers.clear();
							try {
								outerClass.onlineMarkBackToZeroForAClient(ChatTools.user.getName());
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							sendMsg2 = "有一位用户下线了！\n";
							area2.setText("");
							// 要每点击一次发送就要将内容写入输出流中
							try {
								ops.write(sendMsg2.getBytes());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
							table_onlineUser.setText("在线用户(" + outerClass.countOnlineUsers() + ")");
							outerClass.getOnlineUsersName(listUsers);
							for (int i = 0; i < listUsers.size(); i++) {
								utm.add(new Person(listUsers.get(i)));
							}
							table.setItems(utm);
						});
						pSUI.getChildren().addAll(tRoom, tfRoom, btBack, btEnter, btQuit, table);
						FlowPane pTalk = new FlowPane();
						pTalk.setAlignment(Pos.CENTER);
						pTalk.setPrefWidth(260);
						pTalk.setPrefHeight(300);
						pTalk.setPadding(new Insets(25, 25, 25, 25));
						pTalk.setStyle("-fx-border-color: green");
						pTalk.setHgap(10);
						pTalk.setVgap(10);
						// 显示面板
						area1.setPrefWidth(560);
						area1.setPrefHeight(300);
						area1.setEditable(false);
						// 输入面板
						area2.setPrefWidth(560);
						area2.setPrefHeight(60);
						area2.setVisible(true);
						// 发送按钮
						Button btn = new Button("发送");
						pTalk.getChildren().addAll(area1, area2, btn);
						btn.setOnAction(e -> {
							String sendMsg = area2.getText();
							try {
								sendMsg2 = ChatTools.user.getName() + "说：" + sendMsg + "\n";
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							} // 传给服务端的消息要加入换行符，服务端才能知道什么时候结束接收
								// 输入的内容要显示在面板上
							/*
							 * area1.setText( "自己说："+sendMsg+"----"+new
							 * Date(System.currentTimeMillis())+"\n");
							 */
							// 点击发送后，输入面板要清空
							area2.setText("");
							// 要每点击一次发送就要将内容写入输出流中
							try {
								ops.write(sendMsg2.getBytes());
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						});
						pm.setLeft(pSUI);
						pm.setCenter(pTalk);
					});
					// 个人中心
					openMyCenter.setOnAction(actionEvent -> {
						pm.getChildren().clear();
						
						//背景
						ImageView personalB= new ImageView(
								"image/personalBackground.jpg");
//						imagesB.setFitHeight(250);
//						imagesB.setFitWidth(300);
						personalB.fitWidthProperty().bind(pm.widthProperty());
						personalB.fitHeightProperty().bind(pm.heightProperty());
						pm.getChildren().add(personalB);
						
						pm.setTop(menuBar);
						ImageView pmci = new ImageView("image/youngPlant2.png");
						pmci.setFitHeight(160);
						pmci.setFitWidth(160);
						HBox pAll = new HBox();
						pAll.setSpacing(30);
						pAll.setAlignment(Pos.CENTER);
						StackPane pmc = new StackPane(pmci);
						VBox vrn = new VBox();
						vrn.setAlignment(Pos.CENTER);
						vrn.setPadding(new Insets(20, 20, 20, 20));
						vrn.setSpacing(20);
						try {
							Text tn = new Text("个人昵称  " + outerClass.getPersonalInfo(phoneNumber).getString(4));
							tn.setFont(Font.font("KaiTi", FontWeight.NORMAL, 20));
							Text tp = new Text("电话号码  " + outerClass.getPersonalInfo(phoneNumber).getString(1));
							tp.setFont(Font.font("KaiTi", FontWeight.NORMAL, 20));
							vrn.getChildren().addAll(tn, tp);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						pAll.getChildren().addAll(pmc, vrn);
						GridPane vbn = new GridPane();
						vbn.setPadding(new Insets(25, 25, 25, 25));
						vbn.setAlignment(Pos.TOP_CENTER);
						vbn.setHgap(10);
						vbn.setVgap(10);
						Label moneyTitle = new Label("青友币");
						moneyTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						vbn.add(moneyTitle, 0, 1);
						Label moneyAmount;
						try {
							moneyAmount = new Label(outerClass.getPersonalInfo(phoneNumber).getString(3));
							moneyAmount.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
							vbn.add(moneyAmount, 1, 1);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Label babyNameTitle = new Label("青友宠物名:");
						babyNameTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						vbn.add(babyNameTitle, 0, 2);
						TextField babyName = new TextField();
						babyName.setFont(Font.font("Tahoma", FontWeight.NORMAL, 17));
						vbn.add(babyName, 1, 2);
						Label typeTitle = new Label("植株种类");
						typeTitle.setFont(Font.font("YouYuan", FontWeight.NORMAL, 17));
						vbn.add(typeTitle, 0, 3);
						ComboBox<String> cbo = new ComboBox<>();
						cbo.getItems().addAll("芦荟", "常春藤", "文竹", "绿精灵", "玫瑰", "丁香", "薄荷");
						cbo.setValue("选一个你喜欢的");
						vbn.add(cbo, 1, 3);
						Button btn = new Button("         领养         ");
						// 连接数据库
						btn.setOnAction(actionEvent2 -> {
							String strbabyName = babyName.getText();
							try {
								String strSex = outerClass.getPersonalInfo(phoneNumber).getString(5);
								String strRegisterName = outerClass.getPersonalInfo(phoneNumber).getString(4);
								outerClass.storeBabyName(strbabyName, strSex, strRegisterName);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							pmc.getChildren().clear();
							ImageView pmci2 = new ImageView("image/dingxiang.jpg");
							pmci2.setFitHeight(160);
							pmci2.setFitWidth(160);
							pmc.getChildren().add(pmci2);
						});
						btn.setStyle("-fx-base: deepskyblue");
						btn.setTextFill(Color.WHITE);
						vbn.add(btn, 1, 4);
						pm.setCenter(pAll);
						pm.setBottom(vbn);
					});
					Scene scene = new Scene(pm, 1000, 650);
					mainStage.setTitle("青友游戏");
					mainStage.setScene(scene);
					mainStage.show();
				} else {
					System.out.println("登陆失败，请检查账户或密码是否错误！");
					Stage errorStage = new Stage();
					BorderPane pe = new BorderPane();
					StackPane hm = new StackPane();
					Image ie = new Image("image/youngPlant.png");
					ImageView iev = new ImageView(ie);
					iev.setFitWidth(70);
					iev.setFitHeight(70);
					Label le = new Label("登陆失败，请检查账户或密码是否错误！", iev);
					le.setWrapText(true);
					le.setFont(Font.font("NSimSun", FontWeight.NORMAL, 17));
					le.setContentDisplay(ContentDisplay.LEFT);
					hm.getChildren().add(le);
					StackPane hm2 = new StackPane();
					hm2.setPadding(new Insets(25, 25, 25, 25));
					Button bcheck = new Button("确认");
					hm2.getChildren().add(bcheck);
					pe.setCenter(hm);
					pe.setBottom(hm2);
					Scene scene = new Scene(pe, 400, 200);
					errorStage.setTitle("青友游戏");
					errorStage.setScene(scene);
					errorStage.show();
					bcheck.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent ae) {
							errorStage.close();
						}
					});
				}
			}
		});
		btn.setStyle("-fx-base: deepskyblue");
		btn.setTextFill(Color.WHITE);
		Button br = new Button(" 我 要 注 册 ");// registrationModule
		br.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Stage registrationModuleStage = new Stage();
				BorderPane p = new BorderPane();
				ImageView image = new ImageView(("image/background1.jpg"));
				GridPane grid = new GridPane();
				grid.setAlignment(Pos.CENTER);
				grid.setHgap(10);
				grid.setVgap(10);
				Text scenetitle = new Text("用户注册");
				scenetitle.setFill(Color.GRAY); // scenetitle.setTextAlignment(Pos.C);
				scenetitle.setFont(Font.font("YouYuan", FontWeight.BOLD, 25));
				grid.add(scenetitle, 0, 0, 2, 1);
				Label phonenumber = new Label("手机号码:");
				phonenumber.setTextFill(Color.GRAY);
				phonenumber.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(phonenumber, 0, 1);
				TextField userTextField = new TextField();
				userTextField.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(userTextField, 1, 1);
				Label pw = new Label("密码:");
				pw.setTextFill(Color.GRAY);
				pw.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(pw, 0, 2);
				PasswordField pwBox = new PasswordField();
				pwBox.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				grid.add(pwBox, 1, 2);
				Label sex = new Label("性别:");
				sex.setTextFill(Color.GRAY);
				sex.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(sex, 0, 3);
				ComboBox<String> cbo = new ComboBox<>();
				cbo.getItems().addAll("男性", "女性");
				cbo.setValue("男性");
				grid.add(cbo, 1, 3);
				Label rn = new Label("个人昵称:");
				rn.setTextFill(Color.GRAY);
				rn.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(rn, 0, 4);
				TextField rnBox = new TextField();
				rnBox.setFont(Font.font("KaiTi", FontWeight.BOLD, 20));
				grid.add(rnBox, 1, 4);
				VBox Vb = new VBox();
				Vb.setSpacing(10);
				Vb.setAlignment(Pos.CENTER);
				Vb.setPadding(new Insets(5, 5, 5, 5));
				Button bs = new Button("         提 交         ");
				bs.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent event) {
						String phoneNumber = userTextField.getText();
						String pw = pwBox.getText();
						String sex = cbo.getValue();
						String register_name = rnBox.getText();
						outerClass.registe(phoneNumber, pw, sex, register_name);
					}
				});
				Vb.getChildren().add(bs);
				bs.setStyle("-fx-base: deepskyblue");
				bs.setTextFill(Color.WHITE);
				Text t = new Text("青友绿植科技有限公司");
				t.setFill(Color.WHITE);
				t.setFont(Font.font("SimHei", FontWeight.NORMAL, 18));
				Vb.getChildren().add(t);
				image.fitWidthProperty().bind(p.widthProperty());
				image.fitHeightProperty().bind(p.heightProperty());
				p.getChildren().add(image);
				p.setCenter(grid);
				p.setBottom(Vb);
				Scene scene = new Scene(p, 560, 420);
				registrationModuleStage.setTitle("青友系统-注册页面");
				registrationModuleStage.setScene(scene);
				registrationModuleStage.show();
			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.getChildren().add(btn);
		hbBtn.getChildren().add(br);
		grid.add(hbBtn, 1, 4);
		StackPane p3 = new StackPane();
		p3.setAlignment(Pos.CENTER);
		p3.setPadding(new Insets(5, 5, 5, 5));
		Text t = new Text("青友绿植科技有限公司");
		t.setFill(Color.WHITE);
		t.setFont(Font.font("SimHei", FontWeight.NORMAL, 18));
		p3.getChildren().add(t);
		image.fitWidthProperty().bind(p.widthProperty());
		image.fitHeightProperty().bind(p.heightProperty());
		p.getChildren().add(image);
		p.setTop(p1);
		p.setCenter(grid);
		p.setBottom(p3);
		Scene scene = new Scene(p, 600, 460);
		primaryStage.setTitle("青友系统-用户登陆");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				try {
					if(ChatTools.user.getName()=="")
						Platform.exit();
					else
						outerClass.onlineMarkBackToZeroForAClient(ChatTools.user.getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.exit();
			}
		});
	}
 	private void actionServer() throws IOException, SQLException {
		// 1.要得到服务器状态
		System.out.println("lok" + ChatTools.user.getName());
		if (null == cserver) {
			String sPort = tfRoom.getText();// 得到输入的端口
			int port = Integer.parseInt(sPort);
			cserver = new ChatServer(port);
			cserver.start();
			// jf.setTitle("QQ服务器管理程序 :正在运行中");
			btBack.setText("解散房间");
		} else if (cserver.isRunning()) {// 己经在运行
			cserver.stopchatServer();
			cserver = null;
			ChatTools.removeAllClient();
			// 清除所有己在运行的客户端处理线程
			// jf.setTitle("QQ服务器管理程序 :己停止");
			btBack.setText("创建房间");
		}
	}
 	private ContextMenu getTablePop() {
		ContextMenu pop = new ContextMenu();
		MenuItem mi_send = new MenuItem("发信");
		mi_send.setOnAction(e -> {
			String s = "sened";
			try {
				popMenuAction(s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		// 设定菜单命令关键字 哪个菜单项点击了，这个s就是其设定的ActionCommand
		MenuItem mi_del = new MenuItem("踢掉");
		mi_del.setOnAction(e -> {
			String s = "klick";
			try {
				popMenuAction(s);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		// 弹出菜单上的事件监听器对象
		pop.getItems().addAll(mi_send, mi_del);
		return pop;
	}
 	private void popMenuAction(String command) throws IOException, SQLException {
		// 得到在表格上选中的行
		// System.out.print();
		int selectIndex = selectedRow;
		System.out.println(selectIndex);
		Alert warning = new Alert(Alert.AlertType.WARNING, "请选中一个用户");
		LoginInterface.table.setOnMousePressed(e -> {
			MouseButton button = e.getButton();
			switch (button) {
			case SECONDARY:
				if (selectIndex == 0) {
					warning.showAndWait();
					return;
				}
			}
		});
		if (command.equals("del")) {
			// 从线程中移除处理线程对象
			ChatTools.removeClient(selectIndex);
		} else if (command.equals("send")) {
			// UserInfo user = ChatTools.getUser(selectIndex);
			final JDialog jd = new JDialog(jf, true);// 发送对话框
			jd.setLayout(new FlowLayout());
			// jd.setTitle("您将对" + user.getName() + "发信息");
			jd.setSize(200, 100);
			final JTextField jtd_m = new JTextField(20);
			// jtd_m.setPreferredSize(new Dimension(150,30));
			JButton jb = new JButton("发送!");
			// jb.setPreferredSize(new Dimension(50,30));
			jd.add(jtd_m);
			jd.add(jb);
			// 发送按钮的事件实现
			jb.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) throws IOException {
					System.out.println("发送了一条消息...");
					String msg = "房主悄悄说:" + jtd_m.getText();
					ChatTools.sendMsg2One(selectIndex, msg);
					jtd_m.setText("");// 清空输入框
					jd.dispose();
				}
 				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated method stub
				}
			});
			jd.setVisible(true);
		} else {
			// JOptionPane.showMessageDialog(jf, "未知菜单:" + command);
		}
		// 刷新表格
		// table.refresh();
	}
}
