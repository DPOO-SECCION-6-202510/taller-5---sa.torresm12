package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {
	
	private ProductoAjustado productoConAjustes;
	 @BeforeEach
	    void setUp( ) throws Exception
	    {
		 	ProductoMenu productoBase = new ProductoMenu("corral", 14000);
	        productoConAjustes = new ProductoAjustado(productoBase);
	    }

	    @AfterEach
	    void tearDown( ) throws Exception
	    {
	    }

	    @Test
	    void testGetNombre( )
	    {
	        assertEquals( "corral", productoConAjustes.getNombre( ), "El nombre del producto no es el esperado." );
	    }

	    @Test
	    void testGetPrecio( )
	    {
	    	Ingrediente ingrediente = new Ingrediente("lechuga", 1000);
	    	productoConAjustes.getAgregados().add(ingrediente);
	    	int total = 14000 + 1000;
	        assertEquals( total, productoConAjustes.getPrecio( ), "El costo total del producto no es el esperado." );
	    }
	    
	    @Test
	    void testGenerarTextoFactura( )
	    {
	    	Ingrediente ingrediente = new Ingrediente("lechuga", 1000);
	    	productoConAjustes.getAgregados().add(ingrediente);
	    	Ingrediente ingrediente2 = new Ingrediente("tomate", 1000);
	    	productoConAjustes.getEliminados().add(ingrediente2);
	    	StringBuffer sb = new StringBuffer( );
	        sb.append( productoConAjustes.getProductoBase());
	        for( Ingrediente ing : productoConAjustes.getAgregados() )
	        {
	            sb.append( "Nombre agregado: " + ing.getNombre( ) );
	            sb.append( "Precio adicional: " + ing.getCostoAdicional( ) );
	        }
	        for( Ingrediente ing : productoConAjustes.getEliminados())
	        {
	            sb.append( "Nombre eliminado: " + ing.getNombre( ) );
	        }

	        sb.append( "Total: " + 15000 + "\n" );
	        assertEquals( sb.toString(), productoConAjustes.generarTextoFactura(), "El texto de la factura no es el esperado." );
	    }
}
