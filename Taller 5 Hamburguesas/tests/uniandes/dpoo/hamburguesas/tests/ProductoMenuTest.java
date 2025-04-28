package uniandes.dpoo.hamburguesas.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;


public class ProductoMenuTest {
	
	private ProductoMenu producto;
	
	@BeforeEach
	void setUp( ) throws Exception 
	{
		producto = new ProductoMenu("corral", 14000);
	}
	
	@AfterEach
    void tearDown( ) throws Exception
    {
    }
	
	@Test
    void testGetNombre( )
    {
		assertEquals( "corral", producto.getNombre( ), "El nombre del producto no es el esperado." );
	}

	@Test
    void testGetprecio( )
    {
		assertEquals( 14000, producto.getPrecio( ), "El precio del producto no es el esperado." );
	}
	
	@Test
    void testGenerarTextoFactura( )
    {
		StringBuffer sb = new StringBuffer( );
        sb.append( "corral" + "\n" );
        sb.append( "Precio: " + 14000 + "\n" );
		assertEquals( sb.toString( ), producto.generarTextoFactura( ), "El texto de la factura no es el esperado." );
	}
}