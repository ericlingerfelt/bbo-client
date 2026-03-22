package org.bigbangonline.datastructure;

/**
 * The Class MainDataStructure.
 */
public class MainDataStructure extends DataStructure{
	
	/** The Constant SYSTEM_WINDOWS. */
	public final static int SYSTEM_WINDOWS = 0;
	
	/** The Constant SYSTEM_LINUX. */
	public final static int SYSTEM_LINUX = 1;
	
	/** The Constant SYSTEM_MAC. */
	public final static int SYSTEM_MAC = 2;
	
	/** The system. */
	private int system;
	
	/** The debug. */
	private boolean debug;
	
	/** The Constant DEV. */
	public final static int DEV = 0;
	
	/** The Constant NON_DEV. */
	public final static int NON_DEV = 1;
	
	/** The url type. */
	private int urlType;
	
	/** The Constant RATE. */
	public final static int RATE = 0;
	
	/** The Constant BBN. */
	public final static int BBN = 1;
	
	/** The Constant OBS. */
	public final static int OBS = 2;
	
	/** The Constant COS. */
	public final static int COS = 3;
	
	/** The Constant SUITE. */
	public final static int SUITE = 4;
	
	/** The current feature set. */
	private int currentFeatureSet; 
	
	/** The absolute path. */
	private String user, id, pw, absolutePath;
	
	/** The header. */
	private double header;
	
	/** The timeout. */
	private int timeout;

	/** The Constant elementSymbols. */
	private static final String[] elementSymbols = {"n", "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na"
													, "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe" 
													, "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb"
													, "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba"
													, "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu"
													, "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn"
													, "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md"
													, "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Uub", "Uut", "Uuq", "Uup"
													, "Uuh", "Uus", "Uuo", "Uue", "Ubn", "Ubu", "Ubb", "Ubt", "Ubq", "Ubp", "Ubh", "Ubs", "Ubo"
													, "Ube", "Utn", "Utu", "Utb", "Utt", "Utq", "Utp", "Uth", "Uts", "Uto", "Ute", "Uqn", "Uqu"
													, "Uqb", "Uqt", "Uqq", "Uqp", "Uqh", "Uqs", "Uqo", "Uqe", "Upn", "Upu", "Upb", "Upt", "Upq"
													, "Upp", "Uph", "Ups", "Upo", "Upe", "Uhn", "Uhu", "Uhb", "Uht", "Uhq", "Uhp", "Uhh", "Uhs"
													, "Uho", "Uhe", "Usn", "Usu", "Usb", "Ust", "Usq", "Usp"};
	
	/**
	 * Instantiates a new main data structure.
	 */
	public MainDataStructure(){initialize();}
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		//setSystem(0);
		//setURLType(0);
		setHeader(0.1);
		setUser("");
		setID("");
		setPW("");
		setTimeout(30);
		setAbsolutePath("");
		setCurrentFeatureSet(RATE);
		//setDebug(false);
	}
	
	/**
	 * Gets the uRL type.
	 *
	 * @return the uRL type
	 */
	public int getURLType(){return urlType;} 
	
	/**
	 * Sets the uRL type.
	 *
	 * @param urlType the new uRL type
	 */
	public void setURLType(int urlType){this.urlType = urlType;}
	
	/**
	 * Gets the system.
	 *
	 * @return the system
	 */
	public int getSystem(){return system;} 
	
	/**
	 * Sets the system.
	 *
	 * @param system the new system
	 */
	public void setSystem(int system){this.system = system;}
	
	/**
	 * Gets the header.
	 *
	 * @return the header
	 */
	public double getHeader(){return header;} 
	
	/**
	 * Sets the header.
	 *
	 * @param header the new header
	 */
	public void setHeader(double header){this.header = header;}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public String getUser(){return user;} 
	
	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(String user){this.user = user;}
	
	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public String getID(){return id;} 
	
	/**
	 * Sets the iD.
	 *
	 * @param id the new iD
	 */
	public void setID(String id){this.id = id;}
	
	/**
	 * Gets the pW.
	 *
	 * @return the pW
	 */
	public String getPW(){return pw;} 
	
	/**
	 * Sets the pW.
	 *
	 * @param pw the new pW
	 */
	public void setPW(String pw){this.pw = pw;}
	
	/**
	 * Gets the timeout.
	 *
	 * @return the timeout
	 */
	public int getTimeout(){return timeout;} 
	
	/**
	 * Sets the timeout.
	 *
	 * @param timeout the new timeout
	 */
	public void setTimeout(int timeout){this.timeout = timeout;}
	
	/**
	 * Gets the absolute path.
	 *
	 * @return the absolute path
	 */
	public String getAbsolutePath(){return absolutePath;} 
	
	/**
	 * Sets the absolute path.
	 *
	 * @param absolutePath the new absolute path
	 */
	public void setAbsolutePath(String absolutePath){this.absolutePath = absolutePath;}
	
	/**
	 * Gets the current feature set.
	 *
	 * @return the current feature set
	 */
	public int getCurrentFeatureSet(){return currentFeatureSet;} 
	
	/**
	 * Sets the current feature set.
	 *
	 * @param currentFeatureSet the new current feature set
	 */
	public void setCurrentFeatureSet(int currentFeatureSet){this.currentFeatureSet = currentFeatureSet;}
	
	/**
	 * Gets the debug.
	 *
	 * @return the debug
	 */
	public boolean getDebug(){return debug;} 
	
	/**
	 * Sets the debug.
	 *
	 * @param debug the new debug
	 */
	public void setDebug(boolean debug){this.debug = debug;}
	
	/**
	 * Gets the element symbol.
	 *
	 * @param z the z
	 * @return the element symbol
	 */
	public static String getElementSymbol(int z){return elementSymbols[z];}
	
}