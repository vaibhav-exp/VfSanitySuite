package main.java.appmodels;

public class Eribank {
    
    public String packageName = "com.experitest.ExperiBank";
    public String mainActivity = null;
    public String installationPath = null;
    
    
    public LoginScreen loginScreen = new LoginScreen();
    public HomeScreen homeScreen = new HomeScreen();
    public MortgageRequestScreen mortageRequestScreen = new MortgageRequestScreen(); 
    public MakePaymentScreen makePaymentScreen = new MakePaymentScreen();
    
    public Eribank(String pathToApp, String deviceOSType) {
	installationPath = pathToApp;
	mainActivity = packageName;
	if (deviceOSType.equals("android")) {
	    mainActivity =  mainActivity + "/.LoginActivity";
	}; 
    }

    public class LoginScreen {
	 public String usernameField = "xpath=//*[@id='usernameTextField' or @accessibilityLabel='usernameTextField']";
	 public String passwordField = "xpath=//*[@id='passwordTextField' or @accessibilityLabel='passwordTextField']";
	 public String loginButton = "xpath=//*[@id='loginButton' or @accessibilityLabel='loginButton']";
    }
    
    public class HomeScreen {
	 public String mortageRequestButton = "xpath=//*[@id='mortageRequestButton' or @accessibilityLabel='Mortgage Request']";
	 public String makePaymentButton = "xpath=//*[@id='makePaymentButton' or @accessibilityLabel='makePaymentButton']";
	 public String logoutButton = "xpath=//*[@id='logoutButton' or @accessibilityLabel='logoutButton']";
    }
    
    public class MortgageRequestScreen {
	public String nameField = "xpath=//*[@id='nameTextField' or @accessibilityLabel='firstNameTextField']";
	public String lastNameField = "xpath=//*[@id='lastNameTextField' or @accessibilityLabel='lastNameTextField']";
	public String ageField = "xpath=//*[@id='ageTextField' or @accessibilityLabel='ageTextField']";
	public String addressOneField = "xpath=//*[@id='addressOneTextField' or @accessibilityLabel='addressOneTextField']";
	public String addressTwoField =  "xpath=//*[@id='addressTwoTextField' or @accessibilityLabel='addressTwoTextField']";
	public String countryField = "xpath=//*[@id='countryTextField' or @accessibilityLabel='countryTextField']";
	public String countryButton = "xpath=//*[@id='countryButton' or @accessibilityLabel='countryButton']";
	public String countryList = "xpath=//*[@id='countryList' or @accessibilityLabel='conutryView']";
	public String makePaymentView = "xpath=//*[@id='makePaymentView' or @accessibilityLabel='mortageRequestOneView']";
	public String cancelButton = "xpath=//*[@id='cancelButton' or @accessibilityLabel='cancelButton']";
    }

    public class MakePaymentScreen {
	public String nameField = "xpath=//*[@id='nameTextField' or @accessibilityLabel='nameTextField']";
	public String phoneField = "xpath=//*[@id='phoneTextField' or @accessibilityLabel='phoneTextField']";
	public String amountField = "xpath=//*[@id='amountTextField' or @accessibilityLabel='amountTextField']";
	public String countryField = "xpath=//*[@id='countryTextField' or @accessibilityLabel='countryTextField']";
	public String cancelButton = "xpath=//*[@id='cancelButton' or @accessibilityLabel='cancelButton']";
	public String sendPaymentButton = "xpath=//*[@id='sendPaymentButton' or @accessibilityLabel='sendPaymentButton']";	
	public String confirmPaymentDialogMessage = "xpath=//*[@text='Are you sure you want to send payment?']";
    }
    
    
}
