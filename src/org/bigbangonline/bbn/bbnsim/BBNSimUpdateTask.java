package org.bigbangonline.bbn.bbnsim;

import org.bigbangonline.datastructure.MainDataStructure;
import org.bigbangonline.datastructure.bbn.BBNSimDataStructure;
import org.bigbangonline.io.CGICom;

/**
 * The Class BBNSimUpdateTask.
 */
public class BBNSimUpdateTask extends java.util.TimerTask{

	/** The mds. */
	private MainDataStructure mds;
	
	/** The ds. */
	private BBNSimDataStructure ds;
	
	/** The frame. */
	private BBNSimFrame frame;
	
	/** The cgi com. */
	private CGICom cgiCom;

	/**
	 * Instantiates a new bBN sim update task.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 */
	public BBNSimUpdateTask(MainDataStructure mds
							, BBNSimDataStructure ds
							, CGICom cgiCom
							, BBNSimFrame frame){
		this.mds = mds;
		this.ds = ds;
		this.frame = frame;
		this.cgiCom = cgiCom;
	}

	/* (non-Javadoc)
	 * @see java.util.TimerTask#run()
	 */
	public void run(){cgiCom.doCGICall(mds, ds, CGICom.BBN_SIM_UPDATE, frame);}

}