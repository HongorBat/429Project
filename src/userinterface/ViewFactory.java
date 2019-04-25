package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {

	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("TellerView") == true)
		{
			return new TellerView(model);
		}
		else if(viewName.equals("TransactionChoiceView") == true)
		{
			return new TransactionChoiceView(model);
		}
		else if(viewName.equals("AddVendorView") == true)
		{
			return new AddVendorView(model);
		}		
		else if(viewName.equals("AddVIITView") == true)
		{
			return new AddVIITView(model);
		}
		else if(viewName.equals("AddInventoryView") == true)
		{
			return new AddInventoryView(model);
		}
		else if(viewName.equals("UpdateInventoryView") == true)
		{
			return new UpdateInventoryView(model);
		}
		else if(viewName.equals("UpdateFieldView") == true)
		{
			return new UpdateFieldView(model);
		}	
		else if(viewName.equals("ModifyVendorView") == true)
		{
			return new ModifyVendorView(model);
		}	
		else if(viewName.equals("ModifyFieldView") == true)
		{
			return new ModifyFieldView(model);
		}
		else if(viewName.equals("DeleteVIITView") == true)
		{
			return new DeleteVIITView(model);
		}	
		else
			return null;
	}


	/*
	public static Vector createVectorView(String viewName, IModel model)
	{
		if(viewName.equals("SOME VIEW NAME") == true)
		{
			//return [A NEW VECTOR VIEW OF THAT NAME TYPE]
		}
		else
			return null;
	}
	*/

}
