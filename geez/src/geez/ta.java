package geez;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;
import java.io.*;
import java.lang.*;
import java.util.*;



public class ta {
	private static Scanner x;
	private static Scanner y;
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		System.out.println("Enter the user ID:");						//First, we implement some user inputs to create a user.
		Scanner userinput = new Scanner(System.in);
		String username = userinput.nextLine();
		System.out.println("Enter " + username +"'s security level");  		//take the username/security level.
		Scanner seclevel = new Scanner(System.in);
		String securityclear = seclevel.nextLine();
		
	
		
		
		// write it so it prints sec level to authlevel.txt as a string
		String authfile = "authlevel";									// Here, we are going to write the input of the authorization and username level to the file, "authlevel.txt to be used later.
		File authwrite = new File(authfile + ".txt");
		FileWriter sw = new FileWriter(authwrite, true);
		PrintWriter aw = new PrintWriter(sw);
		
		aw.println(username + "," + securityclear);
		aw.close();	
		
		
		
		
		System.out.println("Enter password");							// here we're gonna take the password of the created user.
		Scanner pword = new Scanner(System.in);
		String pazzword = pword.nextLine();
		String algorithm = "SHA-256";			// creating a string to declare the hash algorithm
		System.out.println("We're going to hash this with SHA-256 and store it for you!");
		byte[] Alicesalt = {-63, 116, 113};							// for the sake of time and completion of the project I hard wired ALice's salt to the code, otherwise we would just read it from the file.
		byte[] personal = createSalt();				//creating a personal salt for the new user.
		
		System.out.println("We've also given you a personal salt: "); //printing out the new users salt.
		for(int i=0; i< personal.length ; i++) {
	         System.out.print(personal[i] +" ");
	      }
		System.out.println("");
		String hasheesh = generateHash(pazzword, algorithm, personal);	 // calls the method to generate the new users hash
		                                                             // write the username, HASH, and salt to file USERS.txt
		String filetowrite = "users";							
		File members = new File(filetowrite + ".txt");
		FileWriter fw = new FileWriter(members, true);
		PrintWriter pw = new PrintWriter(fw);
		
		pw.println(username + "," + hasheesh +" ," + "salt:" + personal[0] + " " + personal[1] + " " + personal[2]);
		pw.close();
		
		
		
		System.out.println("Enter your username to login!");			// login inputs, takes in username and password
		String usernamelogin = userinput.nextLine();
		System.out.println("Enter your password to login!");
		String passwordlogin = userinput.nextLine();
		System.out.println("Hashing!");											//hashes the password with the generatehash method. takes in the password, algorithm, and salt.
		String verifypassword = generateHash(passwordlogin, algorithm, Alicesalt);
		System.out.println(verifypassword);
		String filepath = "users.txt";
		String authfiles = "authlevel.txt";
		verifyLogin(usernamelogin,verifypassword,filepath);						// verifies that the password's input hash aligns with the hash in the text file
		if (verifyLogin(usernamelogin,verifypassword,filepath) == true) {		// if it is, we successfully login
			System.out.println("You have successfully logged in.");
			int v = checkauthlevel(usernamelogin, authfiles);					// then we check the authority level, by calling the method, checkauthlevel
			System.out.println("integrity level: " + v);						// prints out the integrity level, for convenience of demonstration, it is equivalent to the authorization level.
			if(v == 4) {
				System.out.println("You have TopSecret Clearance!!");			// printing out what clearance level the user has.
			}
			if(v == 3) {
				System.out.println("You have Secret Clearance!!");
			}
			if(v == 2) {
				System.out.println("You have Classified Clearance!!");
				
			}
			if(v == 1) {
				System.out.println("You have Unclassified Clearance!!");
			}
		
		
			manipulatefiles(v);																	// calling the function to manipulate the files only if login is successful.

		
		
		}
		if (verifyLogin(usernamelogin,verifypassword,filepath) == false) {							// if the login isnt accurate, states so, and the program ends.
			System.out.println("Invalid Credentials! Go away!");
		}
		
			
		};

		
	
		public static void manipulatefiles(int v) throws IOException {					
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("Demonstrate 1) BLP or 2) BIBA? ");				// asks which system would like to be demonstrated.
			Scanner sc= new Scanner(System.in);   
			int b= sc.nextInt();
			
			if(b == 1) {
			System.out.println("Which file would you like to work with? ");
			System.out.println("1: Unclassified || 2: Classified || 3: Secret || 4: TopSecret "); //asks for which file to be altered.
			
			int a= sc.nextInt();   					//asks whether or not you would like to read or write to the file.
			System.out.print("1: Read || 2: Write");  
			Scanner choice2 = new Scanner(System.in);
			int option2 = choice2.nextInt();
			
			if(a==1) {								// a whole bunch of if statements on whether or not someone will be able to write or read to the file according to the BLP system.
				if(v > 1) {
					
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Unclassified.txt");
						Scanner woohoo = new Scanner(file);
						while(woohoo.hasNextLine()) {
						System.out.println(woohoo.nextLine());
						manipulatefiles(v);

						}
					}
					if(option2==2) {
						System.out.println("Due to BLP, we will not let you write your Higher tier authorization level information to a lower tier file. ");
						manipulatefiles(v);
					}
				}
				if(v == 1) {
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Unclassified.txt");
						Scanner woohoo = new Scanner(file);
						while(woohoo.hasNextLine()) {
						System.out.println(woohoo.nextLine());
						manipulatefiles(v);
						}
					}
					if(option2==2) {
						System.out.println("Enter what you would like to write to the file:");
						Scanner userinput = new Scanner(System.in);
						String inpoot = userinput.nextLine();
						
						String authfile = "Unclassified";
						File authwrite = new File(authfile + ".txt");
						FileWriter sw = new FileWriter(authwrite, true);
						PrintWriter aw = new PrintWriter(sw);
						
						aw.println(inpoot);
						aw.close();	
						
						manipulatefiles(v);

					}

				}}
			if(a==2){
				if(v > 2) {
					
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Classified.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);
						}
					}
					if(option2==2) {
						System.out.println("Due to BLP, we will not let you write your Higher tier authorization level information to a lower tier file.");
						manipulatefiles(v);
					}
				}
				if(v == 2) {
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Classified.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);
						}
					}
					if(option2==2) {
						System.out.println("Enter what you would like to write to the file:");
						Scanner userinput = new Scanner(System.in);
						String inpoot = userinput.nextLine();
						
						String authfile = "Classified";
						File authwrite = new File(authfile + ".txt");
						FileWriter sw = new FileWriter(authwrite, true);
						PrintWriter aw = new PrintWriter(sw);
						
						aw.println(inpoot);
						aw.close();	
						
						manipulatefiles(v);

					}

				}
if(v < 2) {
					
					if(option2 == 1) {
						System.out.println("Due to BLP, you are not allowed to read the file because your authorization level and integrity are not high enough.");
						File file = new File("Secret.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);

						}
					}
					if(option2==2) {
						System.out.println("Due to BLP, we will not let you write your Higher tier authorization level information to a lower tier file.");
						manipulatefiles(v);
					}
				}
				
			}
			if(a==3){
				if(v > 3) {
					
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Secret.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);

						}
					}
					if(option2==2) {
						System.out.println("Due to BLP, we will not let you write your Higher tier authorization level information to a lower tier file.");
						manipulatefiles(v);
					}
				}
				if(v == 3) {
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("Secret.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);
						}
					}
					if(option2==2) {
						System.out.println("Enter what you would like to write to the file:");
						Scanner userinput = new Scanner(System.in);
						String inpoot = userinput.nextLine();
						
						String authfile = "Secret";
						File authwrite = new File(authfile + ".txt");
						FileWriter sw = new FileWriter(authwrite, true);
						PrintWriter aw = new PrintWriter(sw);
						
						aw.println(inpoot);
						aw.close();	
						
						manipulatefiles(v);

					}

				}
		if(v < 3) {
					
					if(option2 == 1) {
						System.out.println("Due to BLP, you are not allowed to read the file because your authorization level and integrity are not high enough.");
						File file = new File("Secret.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);

						}
					}
					if(option2==2) {
						System.out.println("Due to BLP, we will not let you write your Higher tier authorization level information to a lower tier file.");
						manipulatefiles(v);
					}
				}
			}
			if(a==4){
				if(v < 4) {
					
					if(option2 == 1) {
						System.out.println("Due to BLP, you are not allowed to read the file because your authorization level and integrity are not high enough.");
						manipulatefiles(v);

						}
					
					if(option2==2) {
						System.out.println("Due to BLP, you are not allowed to write to the file because your authorization level and integrity are not high enough.");
						manipulatefiles(v);
					}
				}
				if(v == 4) {
					if(option2 == 1) {
						System.out.println("Here is what the file states:");
						File file = new File("TopSecret.txt");
						Scanner woohoo2 = new Scanner(file);
						while(woohoo2.hasNextLine()) {
						System.out.println(woohoo2.nextLine());
						manipulatefiles(v);
						}
					}
					if(option2==2) {
						System.out.println("Enter what you would like to write to the file:");
						Scanner userinput = new Scanner(System.in);
						String inpoot = userinput.nextLine();
						
						String authfile = "TopSecret";
						File authwrite = new File(authfile + ".txt");
						FileWriter sw = new FileWriter(authwrite, true);
						PrintWriter aw = new PrintWriter(sw);
						
						aw.println(inpoot);
						aw.close();	
						
						manipulatefiles(v);

					}

				}
				
			}}
			
			if(b == 2) {										// a whole bunch of if statements on whether or not someone will be able to write or read to the file according to the Biba system.
				System.out.println("Which file would you like to work with? ");
				System.out.println("1: Unclassified || 2: Classified || 3: Secret || 4: TopSecret ");
				
				int a= sc.nextInt();  
				System.out.print("1: Read || 2: Write");  
				Scanner choice2 = new Scanner(System.in);
				int option2 = choice2.nextInt();
				
				if(a==1) {
					if(v > 1) {
						
						if(option2 == 1) {
							System.out.println("According to Biba, you are not allowed to read the file due to your high level of integrity.");
							manipulatefiles(v);

							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Unclassified";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);
						}
					}
					if(v == 1) {
						if(option2 == 1) {
							System.out.println("Here is what the file states:");
							File file = new File("Unclassified.txt");
							Scanner woohoo = new Scanner(file);
							while(woohoo.hasNextLine()) {
							System.out.println(woohoo.nextLine());
							manipulatefiles(v);
							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Unclassified";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);

						}

					}
				if(a==2){
					if(v > 2) {
						
						if(option2 == 1) {
							System.out.println("According to Biba, you are not allowed to read the file due to your high level of integrity.");
					
							manipulatefiles(v);
							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Classified";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);
						}
					}
					if(v == 2) {
						if(option2 == 1) {
							System.out.println("Here is what the file states:");
							File file = new File("Classified.txt");
							Scanner woohoo2 = new Scanner(file);
							while(woohoo2.hasNextLine()) {
							System.out.println(woohoo2.nextLine());
							manipulatefiles(v);
							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Classified";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);

						}

					}
					if(v < 2) {
						
						if(option2 == 1) {
							System.out.println("Due to BIBA, you are allowed to read the file because your integrity is lower than the file.");
						
							manipulatefiles(v);

							}
						}
						if(option2==2) {
							System.out.println("Due to BIBA, you are not allowed to write to the file with your lower integrity level");
							manipulatefiles(v);
						}
					
					
		
				if(a==3){
					if(v > 3) {
						
						if(option2 == 1) {
							System.out.println("Due to BIBA, you are not allowed to read the file because your integrity i");
						
							manipulatefiles(v);

							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Secret";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);
						}
					
					if(v == 3) {
						if(option2 == 1) {
							System.out.println("Here is what the file states:");
							File file = new File("Secret.txt");
							Scanner woohoo2 = new Scanner(file);
							while(woohoo2.hasNextLine()) {
							System.out.println(woohoo2.nextLine());
							manipulatefiles(v);
							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "Secret";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);

						}

					}
			if(v < 3) {
						
						if(option2 == 1) {
							if(option2 == 1) {
								System.out.println("Here is what the file states:");
								File file = new File("Secret.txt");
								Scanner woohoo2 = new Scanner(file);
								while(woohoo2.hasNextLine()) {
								System.out.println(woohoo2.nextLine());
								manipulatefiles(v);

							}
						}
						if(option2==2) {
							System.out.println("Due to BIBA, you are not allowed to write to the file with your lower integrity level");
							manipulatefiles(v);
						}
					
						}}}
				if(a==4){
					if(v < 4) {
						
						if(option2 == 1) {
							System.out.println("Here is what the file states:");
							File file = new File("TopSecret.txt");
							Scanner woohoo2 = new Scanner(file);
							while(woohoo2.hasNextLine()) {
							System.out.println(woohoo2.nextLine());
							manipulatefiles(v);

							}}
						
						if(option2==2) {
							System.out.println("You cannot r to the file!!");
							manipulatefiles(v);
						}
					}
					if(v == 4) {
						if(option2 == 1) {
							System.out.println("Here is what the file states:");
							File file = new File("TopSecret.txt");
							Scanner woohoo2 = new Scanner(file);
							while(woohoo2.hasNextLine()) {
							System.out.println(woohoo2.nextLine());
							manipulatefiles(v);
							}
						}
						if(option2==2) {
							System.out.println("Enter what you would like to write to the file:");
							Scanner userinput = new Scanner(System.in);
							String inpoot = userinput.nextLine();
							
							String authfile = "TopSecret";
							File authwrite = new File(authfile + ".txt");
							FileWriter sw = new FileWriter(authwrite, true);
							PrintWriter aw = new PrintWriter(sw);
							
							aw.println(inpoot);
							aw.close();	
							
							manipulatefiles(v);

						}

					}
	
		}}}
			
		
		
			
			
			
			
			
			
		
	
	public static boolean verifyLogin(String username, String password, String filepath) {				// here we implement a method to verify that the hashed password is within the files.
		boolean found = false;															// set found to false
		String tempUsername = "";
		String tempHash = "";
		
		
		try {
			x = new Scanner(new File(filepath));										// scanning the files and searching for the username,hash in this exact format. 
			x.useDelimiter("[,\n]");
			
			while(x.hasNext() && !found) {
				
				if(tempUsername.trim().equals(username.trim()) && tempHash.trim().equals(password.trim())){
					found = true;												// if it finds the file, return true to the found variable!
				}
				tempUsername = x.next();
				tempHash = x.next();
			}
			x.close();
		}
		
		catch(Exception e) {
			
			System.out.println("Error");
			System.out.println(found);

		}
		return found;																				//return the found variable after sacnning the file.		
	}
	
	public static int checkauthlevel(String username, String filepath) {						// utilizing some of the same methods as the method above, we check the file for the authentication level.
		String userrr = ""; 
		String Authlevel = "";
		String tops = "TopSecret";
		String s = "Secret";
		String c = "Classified";
		String uc = "Unclassified";

		int x;
		x = 0;
		boolean found = false;
		try {
		y = new Scanner(new File(filepath));
		y.useDelimiter("[,\n]");
		
		while(y.hasNext()&& !found) {
			userrr = y.next();
			Authlevel = y.next();
			if(userrr.trim().equals(username.trim())){
				found = true;
				System.out.println("Found user!");
			}
		}
		if(found) {
			if(Authlevel.equals(tops.trim())) {
				x = 4;
			} 
			if(Authlevel.equals(s.trim())) {
				x = 3;
			}
			if(Authlevel.equals(c.trim())) {
				x = 2;
			}
			if(Authlevel.equals(uc.trim())) {
				x=1;
			}
			
		}
		
		
	}
		catch(Exception e) {
			return 0;
}
		return x;}
	
	private static String generateHash(String data, String algorithm, byte[] salt) throws NoSuchAlgorithmException {				// here is the method that generates the hash.
		MessageDigest digest = MessageDigest.getInstance(algorithm);					
		digest.reset();
		digest.update(salt);										// updating it with the salt
		byte[] hash = digest.digest(data.getBytes());				// creating the hash in a byte array
		return bytesToStringHex(hash);								// calling the bytes to string method and turn what that returns to this method.
		
	}
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();				// creating a random hex array to the char array.
	
	public static String bytesToStringHex(byte[] bytes) {			// putting the bytes into a string of characters that can be easily read through the prior methods that scan for the hex values in the file.
		char[] hexChars = new char[bytes.length * 2];
		for(int j = 0; j < bytes.length; j ++) {
			int v = bytes[j] & 0xFF;
			hexChars[j*2] = hexArray[v >>> 4];
			hexChars[j*2 + 1] = hexArray[v & 0x0F];

		}
		return new String(hexChars);
	}
	
	public static byte[] createSalt() {						// this method creates the salt value that the hash will use by creating a 3 byte array and randomizing the bytes.
		byte[] bytes = new byte[3];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
		
		
	}
	
	
	
	
	
	
	
}
