package com.uade.tesis.utils;

/**<P><FONT COLOR="red">
*<B>Descripcion:</B><BR>
*<FONT COLOR="blue">
* Class to define the parameter object. A parameter has aditional information which
* will be used by function and it will condition the input image proccess to get the 
* output image. 
* A parameter can be: pBOOL, pINT, pREAL, pSTRING, pIMAGE, pLIST, pDIR and pOBJECT.
* That types are defined in JIP class. Morover a parameter can be required or no.
* In case of it is not required, the parameter can have a default assigned value.
* The basic constructor of parameter should specify its name, type and if it is required
* or not. That characteristics do not change during the object life.
* We can assign a description parameter which shows its operation.
* We can access to value of parameter or assign it. If a parameter do not have
* assigned value and it is not required, when we access to the result, we get the
* default value.   
* @see JIP#pBOOL
* @see JIP#pINT
* @see JIP#pREAL
* @see JIP#pSTRING
* @see JIP#pIMAGE
* @see JIP#pLIST
* @see JIP#pDIR
* @see JIP#pOBJECT
*/
public class JIPParameter {

	/**
	 * Name of parameter
	 * 
	 * @uml.property name="name"
	 */
	String name;

	/**
	 * Type of parameter
	 * 
	 * @uml.property name="type"
	 */
	int type;

	/**
	 * Shows if the parameter is required
	 * 
	 * @uml.property name="required"
	 */
	boolean required;

	/**
	 * Shows if the parameter is setted
	 * 
	 * @uml.property name="asigned"
	 */
	boolean asigned;

	/**
	 * Description of the parameter
	 * 
	 * @uml.property name="description"
	 */
	String description = null;


	//Valores del Parametro

	/** Shows if the parameter is boolean*/
	boolean valbool = false;

	/** Shows if the parameter is integer*/
	int valint = 0;

	/** Shows if the parameter is real*/
	float valreal = 0;

	/** Shows if the parameter is a string text*/	
	String valstr = null;

	/**
	 * Shows if the parameter is an image
	 * 
	 * @uml.property name="valimg"
	 * @uml.associationEnd 
	 * @uml.property name="valimg" multiplicity="(0 1)"
	 */
	JIPImage valimg = null;


	/** Shows if the parameter is a list */
	String []vallist = null;
	
	/** Shows if the parameter is an object */
	Object valobj = null;

	//Default values of parameter

	/** Default boolean value */
	boolean defvalbool = false;

	/** Default integer value */
	int defvalint = 0;

	/** Default real value */
	float defvalreal = 0;

	/** Default string text value */
	String defvalstr = null;

	/**
	 * Default image value
	 * 
	 * @uml.property name="defvalimg"
	 * @uml.associationEnd 
	 * @uml.property name="defvalimg" multiplicity="(0 1)"
	 */
	JIPImage defvalimg = null;


	/** Default object value */
	Object defvalobj = null;

	//******************//
	//  Constructors    //
	//******************//

	/**<P><FONT COLOR="red">
		 * <B>Description:</B><BR>
		 * <FONT COLOR="blue">
	* Parameter contructor.
	* @param n Name of parameter.
	* @param t Type of parameter.
	* @param req Flag which shows if it is required (true) or not (false).
	*/
	public JIPParameter(String n, int t, boolean req) {
		name = n;
		type = t;
		required = req;
		asigned = false;
	}

	//*******************//
	//     METHODS       //
	//*******************//

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">	
	 * It gets the name of the parameter.
	 * @return Name of parameter.
	 * 
	 * @uml.property name="name"
	 */
	public String getName() {
		return name;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It gets the type of parameter.	
	 * @return Type of parameter.
	 * @see JIP#pBOOL
	 * @see JIP#pINT
	 * @see JIP#pREAL
	 * @see JIP#pSTRING
	 * @see JIP#pIMAGE
	 * 
	 * @uml.property name="type"
	 */
	public int getType() {
		return type;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">	
	 * It tells us if the parameter is required or not.
	 * @return true if the parameter is required else return false.
	 * 
	 * @uml.property name="required"
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It tell us if the parameter value is assigned o no.	 
	 * @return true if the parameter value is assigned else return false.
	 * 
	 * @uml.property name="asigned"
	 */
	public boolean isAsigned() {
		return asigned;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It assigns to parameter a description.	 
	 * @param d Description assigns to the parameter.
	 * 
	 * @uml.property name="description"
	 */
	public void setDescription(String d) {
		description = d;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It gets the description that assigns on the parameter.
	 * @return Description assigns to the parameter.
	 * 
	 * @uml.property name="description"
	 */
	public String getDescription() {
		return description;
	}

	// ********************************************
	// Assign and get the default value	
	// ********************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">	 
	 * It assigns default parameter as boolan.
	 * The parameter should not be required and should be pBOOL type.	 
	 * @param d Default value.
	 */
	public void setDefault(boolean d) {
		if (type == JIP.pBOOL)
			defvalbool = d;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns default parameter as integer.
	 * The parameter should not be required and should be pINT type.	 
	 * @param d Default value.
	 */
	public void setDefault(int d) {
		if ((!required) && (type == JIP.pINT))
			defvalint = d;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns default parameter as real.
	 * The parameter should not be required and should be pREAL type.	 
	 * @param d Default value.
	 */
	public void setDefault(float d) {
		if ((!required) && (type == JIP.pREAL))
			defvalreal = d;
	}
	public void setDefault(double d) {
		if ((!required) && (type == JIP.pREAL))
			defvalreal = (float)d;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns default parameter as string.
	 * The parameter should not be required and should be pSTRING or pFILE type.	 
	 * @param d Default value.
	 */
	public void setDefault(String d) {
		if ((!required) && (type == JIP.pSTRING || type == JIP.pFILE 
				|| type == JIP.pDIR))
			defvalstr = d;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns default parameter as image.
	 * The parameter should not be required and should be pIMAGE type.	 
	 * @param img Default value.
	 */
	public void setDefault(JIPImage img) {
		if ((!required) && (type == JIP.pIMAGE))
			defvalimg = new JIPImage(img);
	}

	/**<P><FONT COLOR="red">
	 *<B>Descripcion:</B><BR>
	 *<FONT COLOR="blue">
	 * It assigns default parameter as a list.
	 * The parameter should be pLIST type.	 
	 * @param vlist Default value.
	 */
	public void setDefault(String []vlist) {
		if (type == JIP.pLIST) {
			defvalstr = vlist[0];
			valstr = vlist[0];
			vallist = vlist;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns default parameter as object.
	 * The parameter should not be required and should be pOBJECT type.	 
	 * @param obj Default value.
	 */
	public void setDefault(Object obj) {
		if ((!required) && (type == JIP.pOBJECT))
			defvalobj = obj;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default boolan parameter.
	 * The parameter should not be required and should be pBOOL type.	 
	 * @return Default value.	 
	 */
	public boolean getDefaultBool() {
		if (type == JIP.pBOOL)
			return defvalbool;
		else
			return false;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default integer parameter.
	 * The parameter should not be required and should be pINT type.	 
	 * @return Default value.	
	 */
	public int getDefaultInt() {
		if ((!required) && (type == JIP.pINT))
			return defvalint;
		else
			return 0;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default real parameter.
	 * The parameter should not be required and should be pREAL type.	 
	 * @return Default value.
	 */
	public float getDefaultReal() {
		if ((!required) && (type == JIP.pREAL))
			return defvalreal;
		else
			return 0;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default string parameter.
	 * The parameter should not be required and should be pSTRING type or pFILE.	 
	 * @return Default value.
	 */
	public String getDefaultStr() {
		if ((!required) && (type == JIP.pSTRING || type == JIP.pFILE
				 || type == JIP.pDIR))
			return defvalstr;
		else
			return null;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default image parameter.
	 * The parameter should not be required and should be pIMAGE type.	 
	 * @return Default value.
	 */
	public JIPImage getDefaultImg() {
		if ((!required) && (type == JIP.pIMAGE))
			return defvalimg;
		else
			return null;
	}

	/**<P><FONT COLOR="red">
	 *<B>Descripcion:</B><BR>
	 *<FONT COLOR="blue">
	 * It gets default list parameter.
	 * The parameter should be pLIST type.	 
	 * @return Default value.
	 */
	public String getDefaultList() {
		if (type == JIP.pLIST) return defvalstr;
		else return null;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets default object parameter.
	 * The parameter should not be required and should be pOBJECT type.	 
	 * @return Default value.
	 */
	public Object getDefaultObj() {
		if ((!required) && (type == JIP.pOBJECT))
			return defvalobj;
		else
			return null;
	}

	// **********************************************	
	// Assign and get of parameter value
	// **********************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a boolean value in the parameter.
	 * The parameter should be pBOOL type.	 
	 * @param v Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(boolean v) {
		if (type == JIP.pBOOL) {
			valbool = v;
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a integer value in the parameter.
	 * The parameter should be pINT type.	 
	 * @param v Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(int v) {
		if (type == JIP.pINT) {
			valint = v;
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assgins a real value in the parameter.
	 * The parameter should be pREAL type.	 
	 * @param v Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(float v) {
		if (type == JIP.pREAL) {
			valreal = v;
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assgns a string value in the parameter.
	 * The parameter should be pSTRING, pLIST or pFILE type.	 
	 * @param v Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(String v) {
		if (type == JIP.pSTRING || type==JIP.pLIST || type == JIP.pFILE
				|| type == JIP.pDIR) {
			valstr = v;
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a image value in the parameter.
	 * The parameter should be pIMAGE type.	 
	 * @param img Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(JIPImage img) {
		if (type == JIP.pIMAGE) {
			valimg = new JIPImage(img);
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
	 *<B>Descripcion:</B><BR>
	 *<FONT COLOR="blue">
	 * It assigns a list value in the parameter.
	 * The parameter should be pLIST type.
	 * @param vl Value to assign. After you call this method, the method isAsigned() 
	 * returns true.
	 */
	public void setValue(String []vl) {
		if (type == JIP.pLIST) {
			vallist = vl;
			defvalstr = vl[0];
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns an object value in the parameter.
	 * The parameter should be pOBJECT type.	 
	 * @param obj Value to assign. After you call this method, the method isAsigned() 
	 * returns true.	 */
	public void setValue(Object obj) {
		if (type == JIP.pOBJECT) {
			valobj = obj;
			asigned = true;
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets the boolean value assigned in the parameter.	 
	 * The parameter should be pBOOL type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public boolean getValueBool() {
		if (type == JIP.pBOOL) {
			if (asigned)
				return valbool;
			else if (!required)
				return defvalbool;
			else
				return false;
		} else
			return false;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets the integer value assigned in parameter.	 
	 * The parameter should be pINT type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public int getValueInt() {
		if (type == JIP.pINT) {
			if (asigned)
				return valint;
			else if (!required)
				return defvalint;
			else
				return 0;
		} else
			return 0;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets the real value assigned in parameter.	 
	 * The parameter should be pREAL type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public float getValueReal() {
		if (type == JIP.pREAL) {
			if (asigned)
				return valreal;
			else if (!required)
				return defvalreal;
			else
				return 0;
		} else
			return 0;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">	 
	 * It gets the value of the string assigned to the parameter.	 
	 * The parameter should be pSTRING, pLIST, pDIR or pFILE type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public String getValueStr() {
		if (type == JIP.pSTRING || type == JIP.pLIST || 
			type == JIP.pFILE || type == JIP.pDIR) {
			if (asigned) return valstr;
			else if (!required) return defvalstr;
			     else return null;
		}
		else return null; 
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets the value of the image assigned to the parameter.	 	 
	 * The parameter should be pIMAGE type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public JIPImage getValueImg() {
		if (type == JIP.pIMAGE) {
			if (asigned)
				return valimg;
			else if (!required)
				return defvalimg;
			else
				return null;
		} else
			return null;
	}

	/**<P><FONT COLOR="red">
	 *<B>Descripcion:</B><BR>
	 *<FONT COLOR="blue">
	 * It gets the value of the list assigned to the parameter.	 
	 * The parameter should be pLIST type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public String [] getValueListValues() {
		if (type == JIP.pLIST) return vallist;
		else return null;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It gets the value of the object assigned to the parameter.	 
	 * The parameter should be pOBJECT type.
	 * @return Value to assign. If is not assigned and the parameter is not required
	 * then it return a default value.
	 */
	public Object getValueObj() {
		if (type == JIP.pOBJECT) {
			if (asigned)
				return valobj;
			else if (!required)
				return defvalobj;
			else
				return null;
		} else
			return null;
	}

}
