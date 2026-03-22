package org.bigbangonline.rate;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.util.*;
import org.bigbangonline.io.CGICom;
import org.bigbangonline.datastructure.*;
import org.bigbangonline.datastructure.rate.*;

/**
 * The Class RateSelectorTree.
 */
public class RateSelectorTree extends JTree implements TreeExpansionListener{
	
	/** The ds. */
	private DataStructure ds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The frame. */
	private JFrame frame;
	
	/** The model. */
	private DefaultTreeModel model;
	
	/** The selection model. */
	private DefaultTreeSelectionModel selectionModel;
	
	/** The node. */
	private DefaultMutableTreeNode node;
	
	/** The Constant ALL_LIBRARIES. */
	public static final int ALL_LIBRARIES = 0;
	
	/** The Constant SINGLE_LIBRARY. */
	public static final int SINGLE_LIBRARY = 1;
	
	/** The type. */
	private int type;
	
	/** The used tree paths. */
	private Vector<String> usedTreePaths = new Vector<String>();
	
	/**
	 * Instantiates a new rate selector tree.
	 *
	 * @param mds the mds
	 * @param ds the ds
	 * @param cgiCom the cgi com
	 * @param frame the frame
	 * @param type the type
	 */
	public RateSelectorTree(MainDataStructure mds, DataStructure ds, CGICom cgiCom, JFrame frame, int type){
		
		this.mds = mds;
		this.ds = ds;
		this.cgiCom = cgiCom;
		this.frame = frame;
		this.type = type;
		
		if(type==ALL_LIBRARIES){
			node = new DefaultMutableTreeNode("Libraries");
		}else if(type==SINGLE_LIBRARY){
			node = new DefaultMutableTreeNode("Reaction Rates");
		}
		model = new DefaultTreeModel(node);
		selectionModel = new DefaultTreeSelectionModel();
		selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		setModel(model);
		setEditable(false);
		putClientProperty("JTree.linestyle", "Angled");
		setSelectionModel(selectionModel);
		setShowsRootHandles(true);
		addTreeExpansionListener(this);
		
		validate();
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeExpanded(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeExpanded(TreeExpansionEvent tee){

		TreePath path = tee.getPath();
		
		if(!usedTreePaths.contains(path.toString())){
			
			Object[] pathArray = path.getPath();
			int correctedPathNumber = 0;
			String libPath = "";
			if(type==ALL_LIBRARIES){
				correctedPathNumber = path.getPathCount();
				if(path.getPathCount()>=3){
					libPath = "/" + pathArray[1].toString().toUpperCase() + "/" + pathArray[2];
				}
			}else if(type==SINGLE_LIBRARY){
				correctedPathNumber = path.getPathCount()+2;
				libPath = "/PUBLIC/BBN_ref_01";
			}
			DefaultMutableTreeNode dummy;
			
			switch(correctedPathNumber){
			
				case 3:
					
					usedTreePaths.add(path.toString());
					setPaths(libPath);
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIBRARY_ISOTOPES, frame)){
						DefaultMutableTreeNode libNode = (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
						dummy = libNode.getFirstLeaf();
						if(dummy.toString().equals("dummy")){
							model.removeNodeFromParent(dummy);
						}
			
						RateLibDataStructure rlds = getRateLibDataStructure(libPath);
						if(rlds.getElementDataStructureVector().size()==0){
							DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode("No Isotopes Available");
							model.insertNodeInto(elementNode, libNode, 0);
						}else{
							Iterator<ElementDataStructure> itr = rlds.getElementDataStructureVector().iterator();
							int indexElement = 0;
							while(itr.hasNext()){
								ElementDataStructure eds = itr.next();
								DefaultMutableTreeNode elementNode = new DefaultMutableTreeNode(eds);
								model.insertNodeInto(elementNode, libNode, indexElement);
								elementNode.add(new DefaultMutableTreeNode("dummy"));
								indexElement++;
							}
						}

					}
					break;
					
				case 4:
					
					usedTreePaths.add(path.toString());
					DefaultMutableTreeNode elementNode = (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
					dummy = elementNode.getFirstLeaf();
					if(dummy.toString().equals("dummy")){
						model.removeNodeFromParent(dummy);
					}
					
					RateLibDataStructure rlds = getRateLibDataStructure(libPath);
					ElementDataStructure eds = rlds.getElementDataStructure(pathArray[pathArray.length-1].toString());
					Iterator<IsotopeDataStructure> itrIsotope = eds.getIsotopeDataStructureVector().iterator();
					int indexIsotope = 0;
					while(itrIsotope.hasNext()){
						IsotopeDataStructure ids = itrIsotope.next();
						DefaultMutableTreeNode isotopeNode = new DefaultMutableTreeNode(ids);
						model.insertNodeInto(isotopeNode, elementNode, indexIsotope);
						isotopeNode.add(new DefaultMutableTreeNode("dummy"));
						indexIsotope++;
					}
					break;
					
				case 5:
					
					usedTreePaths.add(path.toString());
					
					DefaultMutableTreeNode isotopeNode = (DefaultMutableTreeNode)tee.getPath().getLastPathComponent();
					dummy = isotopeNode.getFirstLeaf();
					if(dummy.toString().equals("dummy")){
						model.removeNodeFromParent(dummy);
					}
					
					rlds = getRateLibDataStructure(libPath);
					IsotopeDataStructure ids = (IsotopeDataStructure)((DefaultMutableTreeNode)tee.getPath().getLastPathComponent()).getUserObject();
					setPath(libPath);
					setReaction_types("-1");
					setIsotopes(ids.getZ() + "," + ids.getA());
					
					if(cgiCom.doCGICall(mds, ds, CGICom.GET_RATE_LIST, frame)){
						
						Vector<RateDataStructure> rdsv = rlds.getRateDataStructureVector(ids.getZ(), ids.getA());
						Iterator<RateDataStructure> itrRate = rdsv.iterator();
						int indexReaction = 0;
						while(itrRate.hasNext()){
							RateTreeNode reactionNode = new RateTreeNode(itrRate.next());
							model.insertNodeInto(reactionNode, isotopeNode, indexReaction);
							indexReaction++;
							
						}
					}
					break;
			
			}
			
		}
	
		expandPath(path);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.event.TreeExpansionListener#treeCollapsed(javax.swing.event.TreeExpansionEvent)
	 */
	public void treeCollapsed(TreeExpansionEvent tee){}
	
	/**
	 * Gets the rate lib data structure.
	 *
	 * @param libPath the lib path
	 * @return the rate lib data structure
	 */
	private RateLibDataStructure getRateLibDataStructure(String libPath){
		RateLibDataStructure rlds = null;
		if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
			rlds = ((RateVizDataStructure)ds).getRateLibDataStructure(libPath);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
			rlds = ((RateManDataStructure)ds).getRateLibDataStructure(libPath);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
			rlds = ((RateLibManDataStructure)ds).getRateLibDataStructure(libPath);
		}
		return rlds;
	}
	
	/**
	 * Sets the path.
	 *
	 * @param path the new path
	 */
	private void setPath(String path){
		if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
			((RateVizDataStructure)ds).setPath(path);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
			((RateManDataStructure)ds).setPath(path);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
			((RateLibManDataStructure)ds).setPath(path);
		}
	}
	
	/**
	 * Sets the paths.
	 *
	 * @param paths the new paths
	 */
	private void setPaths(String paths){
		if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
			((RateVizDataStructure)ds).setPaths(paths);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
			((RateManDataStructure)ds).setPaths(paths);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
			((RateLibManDataStructure)ds).setPaths(paths);
		}
	}
	
	/**
	 * Sets the isotopes.
	 *
	 * @param isotopes the new isotopes
	 */
	private void setIsotopes(String isotopes){
		if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
			((RateVizDataStructure)ds).setIsotopes(isotopes);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
			((RateManDataStructure)ds).setIsotopes(isotopes);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
			((RateLibManDataStructure)ds).setIsotopes(isotopes);
		}
	}
	
	/**
	 * Sets the reaction_types.
	 *
	 * @param reaction_types the new reaction_types
	 */
	private void setReaction_types(String reaction_types){
		if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateVizDataStructure")){
			((RateVizDataStructure)ds).setReaction_types(reaction_types);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateManDataStructure")){
			((RateManDataStructure)ds).setReaction_types(reaction_types);
		}else if(ds.getClass().toString().equals("class org.bigbangonline.datastructure.rate.RateLibManDataStructure")){
			((RateLibManDataStructure)ds).setReaction_types(reaction_types);
		}
	}
	
	/**
	 * Gets the selected object.
	 *
	 * @return the selected object
	 */
	public RateDataStructure getSelectedObject(){
		try{
			if(((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject() instanceof RateDataStructure){
				return (RateDataStructure)((DefaultMutableTreeNode)getSelectionPath().getLastPathComponent()).getUserObject();
			}
			return null;
		}catch(NullPointerException npe){
			return null;
		}
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param vector the new current state
	 */
	public void setCurrentState(Vector<RateLibDataStructure> vector){
		setCurrentState(vector, null);
	}
	
	/**
	 * Sets the current state.
	 *
	 * @param vector the vector
	 * @param rldsOmitted the rlds omitted
	 */
	public void setCurrentState(Vector<RateLibDataStructure> vector, RateLibDataStructure rldsOmitted){
		
		node.removeAllChildren();
		model.reload();
		usedTreePaths = new Vector<String>();

		if(type==ALL_LIBRARIES){

			DefaultMutableTreeNode publicNode = new DefaultMutableTreeNode("Public");
			DefaultMutableTreeNode sharedNode = new DefaultMutableTreeNode("Shared");
			DefaultMutableTreeNode userNode = new DefaultMutableTreeNode("User");
			
			model.insertNodeInto(publicNode, node, 0);
			model.insertNodeInto(sharedNode, node, 1);
			model.insertNodeInto(userNode, node, 2);
			
			Iterator<RateLibDataStructure> itr = vector.iterator();
			while(itr.hasNext()){
				RateLibDataStructure rlds = itr.next();
				if(rldsOmitted!=null){
					if(!(rlds.getPath() + rlds.getName()).equals(rldsOmitted.getPath() + rldsOmitted.getName())){

						RateLibTreeNode node = new RateLibTreeNode(rlds);
						if(rlds.getPath().equals("/PUBLIC/")){
							publicNode.add(node);
							node.add(new DefaultMutableTreeNode("dummy"));
						}else if(rlds.getPath().equals("/SHARED/")){
							sharedNode.add(node);
							node.add(new DefaultMutableTreeNode("dummy"));
						}else if(rlds.getPath().equals("/USER/")){
							userNode.add(node);
							node.add(new DefaultMutableTreeNode("dummy"));
						}
						
					}

				}else{
					
					RateLibTreeNode node = new RateLibTreeNode(rlds);
					if(rlds.getPath().equals("/PUBLIC/")){
						publicNode.add(node);
						node.add(new DefaultMutableTreeNode("dummy"));
					}else if(rlds.getPath().equals("/SHARED/")){
						sharedNode.add(node);
						node.add(new DefaultMutableTreeNode("dummy"));
					}else if(rlds.getPath().equals("/USER/")){
						userNode.add(node);
						node.add(new DefaultMutableTreeNode("dummy"));
					}
					
				}
				
			}
			
			if(publicNode.getChildCount()==0){
				node.remove(publicNode);
			}
			if(sharedNode.getChildCount()==0){
				node.remove(sharedNode);
			}
			if(userNode.getChildCount()==0){
				node.remove(userNode);
			}
		
		}else if(type==SINGLE_LIBRARY){
			node.add(new DefaultMutableTreeNode("dummy"));
		}
		
	}

}

