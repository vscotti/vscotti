package com.uade.tesis.utils;

import java.util.Vector;

/**<P><FONT COLOR="red">
*<B>Descripcion:</B><BR>
*<FONT COLOR="blue">
* Class to define function objects. A function is used to process an image or
* an input sequence to return an image or output sequence and another additional result.
* A function can or can not have a parameter or associated results and it can know or 
* assign its actuals or defaults values. When you can access to the parameters and
* results, you access it by name. Moreover, a function has assigned a name and a operation
* description that it does in the input image.
* We can not create function class objects directly, we have to create subclass and 
* when they inherit the class then they implement the creator that it has to assign the name,
* the description, the parameters and results and the processImg() method that it will be
* which take an input image and function parameters and it will return an output image and 
* the addicional results.
* @see JIPParameter
*/
public abstract class JIPFunction {

	/**
	 * This variable indicates the name of the group at which this function is 
	 * assigned.
	 * 
	 * @uml.property name="groupFunc"
	 */
	protected int groupFunc=JIPFunctionList.OTHERS;

	/**
	 * Variable which contain the name of the function.
	 * It is accesible in the derivative class which should assign that variable in
	 * its constructor.
	 * 
	 * @uml.property name="name"
	 */
	protected String name;

	/**
	 * Variable which contain the description of the function.
	 * It is accesible in the derivative class which should assign that variable in
	 * its constructor.
	 * 
	 * @uml.property name="description"
	 */
	protected String description;


	/**
	 * Variable which contain the parameters vector of the function.
	 * It is accesible in the derivative class which should assign that variable in
	 * its constructor to define the parameter of the function.
	 * 
	 * @uml.property name="params"
	 * @uml.associationEnd 
	 * @uml.property name="params" multiplicity="(0 -1)" elementType="jip.JIPParameter"
	 */
	protected Vector params = new Vector();

	/**
	 * Variable which contain a output parameter vector (results) of the function.
	 * It is accesible in the derivative class which should assign that variable in
	 * its constructor to define the results of the function.
	 * 
	 * @uml.property name="results"
	 * @uml.associationEnd 
	 * @uml.property name="results" multiplicity="(0 -1)" elementType="jip.JIPParameter"
	 */
	protected Vector results = new Vector();

	/**
	 * Variable which contain an error if it has been generated.
	 * 
	 * @uml.property name="error"
	 */
	protected String error = null;

	/**
	 * Variable which contain a warning if it has been generated.
	 * 
	 * @uml.property name="warning"
	 */
	protected String warning = null;

	/**
	 * Variable which contain some information if it has been generated.
	 * 
	 * @uml.property name="info"
	 */
	protected String info = null;

	/**
	 * Variable which contain the execution time of the last execution of this function.
	 * 
	 * @uml.property name="execTime"
	 */
	protected long execTime = 0;


	//******************//
	//  Constructores   //
	//******************//

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
	 		 *<FONT COLOR="blue">
	* Function constructor. The derivative class should implement asigned varibles
	* name, description, params y results.
	*/
	public JIPFunction() {
	}

	//*******************//
	//     METODOS       //
	//*******************//

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It obtains the name of the function.
	 * @return Name of the function.
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
	 * It obtains the description of the function. 
	 * @return Description of the function.
	 * 
	 * @uml.property name="description"
	 */
	public String getDescription() {
		return description;
	}



	//***********************************
	// METODOS QUE IMPLEMENTAN LA FUNCION 
	//***********************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	*     It obtains an output image from a input image. This methot should implement
	* by derivative class and it is the method which distinguish the functions. If the
	* function has parameters the values have to asigned the required values before, like this, 
	* the method can access to the correct values. If the function has some results, it should
	* asign them values at the moment, then, After, it is poblible to access to the correct values.
	* @param img Input image.
	* @return Ouput image.
	*/
	public abstract JIPImage processImg(JIPImage img);

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	*	   It obtains an output secuence from a input secuence.
	* Its default setting obtains the output sequence, for it, it uses the processImg()
	* method for the every images of the sequence.  In case of the input image in the processImg() method
	* returns null, the image in the output sequence will be a copy to the input sequence. 
	* If you want to change this behaviour, you should rewrite the derivative class method
	* In case of that function does an additional results, we will have the last sequence frame.
	* You can change this default behaviour if you rewrite the method, then it will access to the indivitual
	* frame results, and it going to acumulate, show and store, ...   
	* @param seq Input sequence.
	* @return Output sequence.
	*/
	public JIPSequence processSeq(JIPSequence seq) {
		JIPSequence res = null;
		JIPImage img = null;
		int nf = seq.nframes;
		if (nf > 0) {
			res = new JIPSequence();
			for (int i = 0; i < nf; i++) {
				img = processImg(seq.getFrame(i));
				img.setName(seq.getFrame(i).getName());
				if (img != null) res.addFrame(img);
				else  res.addFrame(new JIPImage(seq.getFrame(i)));	
			}
			res.setName(seq.getName());
		}
		return res;
	}

	//*************************************
	// METODOS PARA CHEQUEAR LOS PARAMETROS
	//*************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the number of parameters of the function.	 
	 * @return Number of parameters.
	 */
	public int getNumParams() {
		return params.size();
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
		 *<FONT COLOR="blue">
    * It obtains the number of results of the function.	
	* @return Number of results.
	*/
	public int getNumResults() {
		return results.size();
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains every parameters name of the function.	 
	 * @return Vector with parameters name of the function.
	 * null if the function has not parameters.
	 */
	public String[] getParamNames() {
		String[] res = null;
		int nparams = params.size();
		if (nparams > 0)
			res = new String[nparams];
		for (int i = 0; i < nparams; i++)
			res[i] = ((JIPParameter) params.elementAt(i)).getName();
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains every results names of the function.	 
	 * @return Vector with function results names.
	 * null if function has not results.
	 */
	public String[] getResultNames() {
		String[] res = null;
		int nresults = results.size();
		if (nresults > 0)
			res = new String[nresults];
		for (int i = 0; i < nresults; i++) 
			res[i] = ((JIPParameter) results.elementAt(i)).getName();
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	* Tell us if there is or not a parameter of the function with the specific name.
	* @param nom Name to check if it is or not a function parameter.
	* @return true if function has a parameter which is equal to nom. else function reurn false.  
	*/
	public boolean isParam(String nom) {
		boolean res = false;
		for (int i = 0; i < params.size(); i++) {
			if (nom.equals(((JIPParameter) params.elementAt(i)).getName())) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	* Tell us if there is o not a result of the function with the specific name.	 
	* @param nom Name to check if it is or not a function result.
	* @return true if function has a result which is equal to nom. else function return false	 
	*/
	public boolean isResult(String nom) {
		boolean res = false;
		for (int i = 0; i < results.size(); i++) {
			if (nom.equals(((JIPParameter) results.elementAt(i)).getName())) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	* Tell us if a parameter of the function is required.	 
	* @param nom Name to check if it is or not a required parameter.
	* @return true if function has a parameter which name is nom and this is required, else return false. 
	*/
	public boolean isParamRequired(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.isRequired()) {
				res = true;
				break;
			}
		}
		return res;
	}


	/**<P><FONT COLOR="red">
		 *<B>Descripcion:</B><BR>
			 *<FONT COLOR="blue">
	* Tell us if the result of the function is required.
	* @param nom Name to check if it is or not a required result.
	* @return true if function has a result which name is nom and it is 
	* required else return false.
	*/
	public boolean isResultRequired(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.isRequired()) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * Tell us if a parameter of the function has assigned a value.	 
	 * @param nom Name to check if it is or not a assigned value parameter.
	 * @return true if function has a parameter which name is nom and it has
	 * assigned value else return false.
	 */
	public boolean isParamAsigned(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.isAsigned()) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
		 *<FONT COLOR="blue">
	* Tell us if a function result has assigned value.	
	* @param nom Name to check if it es or not a assigned value result.
	* @return true if function has a result which name is nom and it has assigned value.
	* else return false. 
	*/
	public boolean isResultAsigned(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.isAsigned()) {
				res = true;
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Descripcion:</B><BR>
		 *<FONT COLOR="blue">
	* It get the function parameter type.	
	* @param nom Parameter name which returns its type.
	* @return Type of parameter. If it does not exist then it returns -1.
	*/
	public int getParamType(String nom) {
		JIPParameter p = null;
		int res = -1;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName())) {
				res = p.getType();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It get the function result type.	 
	 * @param nom result name which returns its type.
	 * @return Result type. If it does not exist it returns -1.
	 */
	public int getResultType(String nom) {
		JIPParameter p = null;
		int res = -1;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName())) {
				res = p.getType();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * Get a description of the parameter of the function.
	 * @param nom Name of parameter which returns its description.
	 * @return Description of parameter. If it does not exist it returns null.	 
	 */
	public String getParamDescr(String nom) {
		JIPParameter p = null;
		String res = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName())) {
				res = p.getDescription();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * Get the function result description.
	 * @param nom Name of result which returns its description.
	 * @return Description of result. If it does not exist it returns null.	 
	 */
	public String getResultDescr(String nom) {
		JIPParameter p = null;
		String res = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName())) {
				res = p.getDescription();
				break;
			}
		}
		return res;
	}

	//**********************************************
	// METODOS PARA ASIGNAR VALORES A LOS PARAMETROS
	//**********************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a boolean value in some parameter of the function.	 
	 * @param nom Name of parameter to assign (It should exist and be a pBOOL type)
	 * @param v Value to assign.
	 */
	public void setParamValue(String nom, boolean v) {
		JIPParameter p = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pBOOL) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a integer value in some parameter of the function.
	 * @param nom Name of parameter to assign (It should exist and be a pINT type)
	 * @param v Value to assign.
	 */
	public void setParamValue(String nom, int v) {
		JIPParameter p = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pINT) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a real value in some parameter of the function.	 
	 * @param nom Name of Parameter to assign (It should exist and be a pREAL type)
	 * @param v Value to assign.
	 */
	public void setParamValue(String nom, float v) {
		JIPParameter p = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pREAL) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a string value in some parameter of the function.	 
	 * @param nom Name of Parameter to assign (It should exist and be a pSTRING type)
	 * @param v Value to assign.
	 */
	public void setParamValue(String nom, String v) {
		JIPParameter p = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) &&  (p.getType() == JIP.pSTRING 
					|| p.getType() == JIP.pLIST || p.getType() == JIP.pFILE
					|| p.getType() == JIP.pDIR)) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a value of the image in some parameter of the function.	 
	 * @param nom Name of parameter to assign (It should exist and be a pIMAGE type)
	 * @param img Value to assign.
	 */
	public void setParamValue(String nom, JIPImage img) {
		JIPParameter p = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pIMAGE) {
				p.setValue(img);
				break;
			}
		}
	}

	//***************************************************
	// METODOS PARA OBTENER LOS VALORES DE LOS PARAMETROS
	//***************************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtain the boolean value which has assigned in a function parameter.
	 * @param nom Name of the parameter. (It should exist and be pBOOL type)
	 * @return Value of the assigned parameters. If parameter is not assigned
	 * and it is not required it returns a default assigned value.	 
	 */
	public boolean getParamValueBool(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pBOOL) {
				res = p.getValueBool();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	* It obtains the integer value which has assigned in a parameter of the function.	 
	* @param nom Name of the parameter. (It should exist and be pINT type)
	* @return Value of the assigned parameter. If parameter is not assigned
	* and it is not required it returns a default assigned value.
	*/
	public int getParamValueInt(String nom) {
		JIPParameter p = null;
		int res = 0;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pINT) {
				res = p.getValueInt();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	* It obtains the REAL value which has assigned in a parameter of the function.	 
	* @param nom Parameter name. (It should exist and be pREAL type)
	* @return Value of the assigned parameter. If parameter is not assigned
	* and it is not required it returns a default assigned value.
	*/
	public float getParamValueReal(String nom) {
		JIPParameter p = null;
		float res = 0;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pREAL) {
				res = p.getValueReal();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
		 *<FONT COLOR="blue">
	* It obtains the string value which has assigned in a function parameter.	 
	* @param nom Parameter name. (It should exist and be pSTRING type)
	* @return Value of the assigned parameter. If parameter is not assigned
	* and it is not required it returns a default assigned value.
	*/
	public String getParamValueStr(String nom) {
		JIPParameter p = null;
		String res = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && (p.getType() == JIP.pSTRING 
					|| p.getType() == JIP.pLIST || p.getType() == JIP.pFILE
					|| p.getType() == JIP.pDIR)) { 
				res = p.getValueStr();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the image value which has assigned in a function parameter.	 
	 * @param nom Parameter name. (It should exist and be pIMAGE type)
	 * @return Value of the assigned parameter. If parameter is not assigned
	 * and it is not required it returns a default assigned value.
	 */
	public JIPImage getParamValueImg(String nom) {
		JIPParameter p = null;
		JIPImage res = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pIMAGE) {
				res = p.getValueImg();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 * It Returns the list value which has assigned in a parameter of the function.	 
	 * @param nom Name of parameter. (It should exist and be pLIST type)
	 * @return Value of assigned parameter. If parameter is not assigned
	 * and it is not required it returns a default assigned value.
	 */
	public String[] getParamValueList(String nom) {
		JIPParameter p = null;
		String[] res = null;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pLIST) {
				res = p.getValueListValues();
				break;
			}
		}
		return res;
	}

	//**********************************************
	// METODOS PARA ASIGNAR VALORES A LOS RESULTADOS
	//**********************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a boolean value in a function result.	 
	 * @param nom Name of the result to assign (It should exist and be pBOL type)
	 * @param v Value to assign.
	 */
	public void setResultValue(String nom, boolean v) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pBOOL) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns an integer value in a results of the function.	 
	 * @param nom Name of the result to assign (It should exist and be pINT type)
	 * @param v Value to assign.
	 */
	public void setResultValue(String nom, int v) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pINT) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a real value in a result of thefunction.	 
	 * @param nom Name of the result to assign (It should exist and be pREAL type)
	 * @param v Value to assign.
	 */
	public void setResultValue(String nom, float v) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pREAL) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a string value in a result of the function.	 
	 * @param nom Name of the result to assign (It should exist and be pSTRING type)
	 * @param v Value to assign.
	 */
	public void setResultValue(String nom, String v) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pSTRING) {
				p.setValue(v);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns an image value in a result of the function.	 
	 * @param nom Name of the result to assign (It should exist and be pIMAGE type)
	 * @param img Value to assign.
	 */
	public void setResultValue(String nom, JIPImage img) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pIMAGE) {
				p.setValue(img);
				break;
			}
		}
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It assigns a generic object value in a result of the function.	 
	 * @param nom Name of the result to assign (It should exist and be object type)
	 * @param obj Value to assign.
	 */
	public void setResultValue(String nom, Object obj) {
		JIPParameter p = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pOBJECT) {
				p.setValue(obj);
				break;
			}
		}
	}

	//***************************************************
	// METODOS PARA OBTENER LOS VALORES DE LOS RESULTADOS
	//***************************************************

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the boolean value which has assigned in a result of the function.	 
	 * @param nom Name of the result (It should exist and be pBOOL type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */
	public boolean getResultValueBool(String nom) {
		JIPParameter p = null;
		boolean res = false;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pBOOL) {
				res = p.getValueBool();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the integer value which has assigned in a result of the function.	 
	 * @param nom Name of the result (It should exist and be pINT type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */
	public int getResultValueInt(String nom) {
		JIPParameter p = null;
		int res = 0;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pINT) {
				res = p.getValueInt();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the real value which has assigned in a result of the function.	 
	 * @param nom Name of the result (It should exist and be pREAL type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */
	public float getResultValueReal(String nom) {
		JIPParameter p = null;
		float res = 0;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pREAL) {
				res = p.getValueReal();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the string value which has assigned in a result of the function.	 
	 * @param nom Name of the result (It should exist and be pSTRING type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */	 
	public String getResultValueStr(String nom) {
		JIPParameter p = null;
		String res = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pSTRING) {
				res = p.getValueStr();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the image value which has assigned in a result of the function.	 
	 * @param nom Name of the result (It should exist and be pIMAGE type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */
	public JIPImage getResultValueImg(String nom) {
		JIPParameter p = null;
		JIPImage res = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pIMAGE) {
				res = p.getValueImg();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It obtains the object value which has assigned in a result of the function.	 
	 * @param nom Result name (It should exist and be pOBJECT type)
	 * @return Value of the assigned result. If result is not assigned and
	 * is not required it returns a default value assigned.
	 */
	public Object getResultValueObj(String nom) {
		JIPParameter p = null;
		Object res = null;
		for (int i = 0; i < results.size(); i++) {
			p = (JIPParameter) results.elementAt(i);
			if (nom.equals(p.getName()) && p.getType() == JIP.pOBJECT) {
				res = p.getValueObj();
				break;
			}
		}
		return res;
	}

	/**<P><FONT COLOR="red">
		 *<B>Description:</B><BR>
			 *<FONT COLOR="blue">
	 * It checks if every function the required parameters have an assigned value.	 
	 * @return true if every function required parameter have some assigned value.
	 * or if the function does not have parameters. Else return false.
	 */
	public boolean paramsOK() {
		JIPParameter p = null;
		boolean ok = true;
		for (int i = 0; i < params.size(); i++) {
			p = (JIPParameter) params.elementAt(i);
			if (p.isRequired() && (!p.isAsigned())) {
				ok = false;
				break;
			}
		}
		return ok;
	}
	
	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 * It returns if there was some error when function was used.	 
	 * @return true if there was some error. Else return false.
	 */
	public boolean isError() {
		return error!=null;
	}
	
	/**<P><FONT COLOR="red">
	 *<B>Description:</B><BR>
	 *<FONT COLOR="blue">
	 * It returns if there was some error when function was used.	 
	 * @return true if there was some warning. Else return false.
	 */
	public boolean isWarning() {
		return warning!=null;
	}
	
	/**<P><FONT COLOR="red">
	 *<B>Descripction:</B><BR>
	 *<FONT COLOR="blue">
	 * It returns if there was some information when function was used.	 
	 * @return true if there was some information. Else return false.
	 */
	public boolean isInfo() {
		return info!=null;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It returns an error message of the function.	 
	 * @return The generated error.
	 * 
	 * @uml.property name="error"
	 */
	public String getError() {
		return error;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It returns the warning message of the function.	 
	 * @return The generated warning.
	 * 
	 * @uml.property name="warning"
	 */
	public String getWarning() {
		return warning;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It returns the information message of the function.	 
	 * @return The generated information.
	 * 
	 * @uml.property name="info"
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It returns the group at which this function is assigned.	 
	 * @return A integer indicating the group.
	 * 
	 * @uml.property name="groupFunc"
	 */
	public int getGroupFunc() {
		return groupFunc;
	}

	/**
	 * <P><FONT COLOR="red">
	 * <B>Description:</B><BR>
	 * <FONT COLOR="blue">
	 * It returns the group at which this function is assigned.	 
	 * @param gf A integer indicating the group.
	 * 
	 * @uml.property name="groupFunc"
	 */
	public void setGroupFunc(int gf) {
		groupFunc = gf;
	}

}
