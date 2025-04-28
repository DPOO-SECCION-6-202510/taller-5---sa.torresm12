package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {

	private Combo combo;
	ArrayList<ProductoMenu> items = new ArrayList<>();
	
	@BeforeEach
    void setUp( ) throws Exception
    {
		
		ProductoMenu producto1 = new ProductoMenu("corral", 14000);
		ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
		ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
		items.add(producto1);
		items.add(producto2);
		items.add(producto3);
        combo = new Combo( "combo corral", 0.10, items);
    }
	
	 @AfterEach
	    void tearDown( ) throws Exception
	    {
	    }
	 
	 @Test
	    void testGetNombre( )
	    {
	        assertEquals( "combo corral", combo.getNombre( ), "El nombre del combo no es el esperado." );
	    }
	 
	 @Test
	    void testGetPrecio( )
	    {
		 	int precio = 0;
		 	for(int i = 0; i < items.size(); i++) {
		 		precio += items.get(i).getPrecio();
		 	}
		 	int descuento = ( int ) ( precio * 0.10);
		 	precio -= descuento;
	        assertEquals( precio, combo.getPrecio( ), "El precio del combo no es el esperado." );
	    }
	 
	 @Test
	    void testGenerarTextoFactura( )
	    {
	        StringBuffer sb = new StringBuffer( );
	        sb.append( "Combo " + "combo corral" + "\n" );
	        sb.append( " Descuento: " + 0.10 + "\n" );
	        sb.append( " Costo final: " + 22050 + "\n" );
	        assertEquals( sb.toString(), combo.generarTextoFactura( ), "El texto de la factura no es el esperado." );
	    }
}
