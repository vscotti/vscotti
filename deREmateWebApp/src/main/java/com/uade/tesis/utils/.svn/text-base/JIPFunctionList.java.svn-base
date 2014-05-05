package com.uade.tesis.utils;

//import java.awt.Menu;
//import java.awt.MenuItem;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.io.File;


/**<P><FONT COLOR="red">
   *<B>Description:</B><BR>
   *<FONT COLOR="blue">
   * Class which has the applicable function list.
   * In this class is where the functions and its groups are especified.   
   */
public class JIPFunctionList {
	/** Number of functions in the list */
	int nfunc;
	/** Array which has the names of the functions */
	String[] funcnames = null;
	/** Array which has the names of the groups */
	String[] groupnames = null;
	/** Array which connect function with groups */
	int[] funcgroups = null;
	/** Array keeping the number of functions in each group */
	int[] fgnum = null;
	
	final static public int NGRPS=12; //This element controls the number of groups.
	final static public int TRANSFORM=0;
	final static public int ADJUSTMENTS=1;
	final static public int SMOOTH=2;
	final static public int CONVOLUTION=3;
	final static public int MANIPULATION=4;
	final static public int GEOMETRY=5;
	final static public int EDGES=6;
	final static public int MATH_MORPH=7;
	final static public int APPLIC=8;
	final static public int IMAGEBD=9;
	final static public int RINGPROJECTION=10; 
	final static public int OTHERS=11;

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *		Class constructor. Here the names of the function are inserted in the
	 * arrays and its groups.
	 */
	public JIPFunctionList() {
		// Se cargan las funciones de forma dinámica
		File f = new File("bin/jipfunc");
//		File f = new File("jipfunc");
		
		String []funcs = f.list();
		nfunc = 0;
		for (int i=0; i<funcs.length; i++)
			if (funcs[i].matches("F\\w*.class")) nfunc++;
		funcnames = new String[nfunc];
		funcgroups = new int[nfunc];
		fgnum = new int[NGRPS];
		for (int i=0; i<NGRPS; i++) fgnum[i]=0;
		int cont=0;
		for (int i=0; i<funcs.length; i++)
			if (funcs[i].matches("F\\w*.class")) {
				funcnames[cont] = funcs[i].substring(0,funcs[i].indexOf(".class"));
				Object objeto = null;
				try {
					Class clase = Class.forName("jipfunc." + funcnames[cont]);
					objeto = clase.newInstance();
				} catch (Exception e) {
					System.err.println(e);
				}
				if (objeto != null) {
					funcgroups[cont] = ((JIPFunction) objeto).getGroupFunc();
					fgnum[funcgroups[cont]]++;
					cont++;
				}
			}

		groupnames = new String[NGRPS];
		groupnames[TRANSFORM] = "Transform";
		groupnames[ADJUSTMENTS] = "Adjustments";
		groupnames[SMOOTH] = "Smooth";
		groupnames[CONVOLUTION] = "Convolution";
		groupnames[MANIPULATION] = "Manipulation";
		groupnames[GEOMETRY] = "Geometry";
		groupnames[EDGES] = "Edges";
		groupnames[MATH_MORPH] = "Math Morph";
		groupnames[APPLIC] = "Applications";
		groupnames[IMAGEBD] = "ImageBD";
		groupnames[RINGPROJECTION] = "Ring Projection"; 
		groupnames[OTHERS] = "Others...";
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to get the number of created function.
	 * @return Number of functions
	 */
	public int getNumFunctions() {
		return nfunc;
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to get the name of a group	 
	 * @param g Number of the group
	 * @return Name of the specified group
	 */
	public String getNameGroup(int g) {
		return groupnames[g];
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to get the number of functions in each group.
	 * @return Array where each element is the number of functions of the
	 * corresponding group
	 */
	public int[] getFuncGroupNum() {
		return fgnum;
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to get the name of the function name which is passed by parameter.	 
	 * @param f Number assigned to function
	 * @return Name of the asked function
	 */
	public String getName(int f) {
		if (f >= 0 && f < nfunc) return funcnames[f];
		else return ("");
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to get the group number to which a function owns.	 
	 * @param f Number assigned to function
	 * @return Number of the group
	 */
	public int getNumGroup(int f) {
		return funcgroups[f];
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *	 Method to get the function object such as we know the name and we pass by parameter
	 * @param fname Name of the function
	 * @return JIPFuncion object which has the required function
	 */
	public JIPFunction getJIPFunction(String fname) {
		JIPFunction func = null;
		for (int i = 0; i < nfunc; i++) {
			if (fname.compareTo(funcnames[i]) == 0) {
				Object objeto = null;
				try {
					Class clase = Class.forName("jipfunc." + fname);
					objeto = clase.newInstance();
				} catch (Exception e) {
					System.err.println(e);
				}
				if (objeto != null)
					func = (JIPFunction) objeto;
				break;
			}
		}
		return func;
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 *   Method to create the menu that contain the function.
	 * @param tittle Menu tittle
	 * @param al ActionListener
	 * @return menu that contain the function.
	 */
	public JMenu getFunctionMenu(String tittle, ActionListener al) {
		JMenu mfunc = new JMenu(tittle);
		JMenuItem item;
		JMenu m;

		// Insertamos grupos de funciones y sus funciones
		for (int i = 0; i < NGRPS; i++) {
			m = new JMenu(groupnames[i]);
			for (int j = 0; j < nfunc; j++) {
				if (funcgroups[j] == i) {
					item = new JMenuItem(funcnames[j]);
					item.setActionCommand("F_" + funcnames[j]);
					item.addActionListener(al);
					m.add(item);
				}
			}
			mfunc.add(m);
		}
		return mfunc;
	}
}
