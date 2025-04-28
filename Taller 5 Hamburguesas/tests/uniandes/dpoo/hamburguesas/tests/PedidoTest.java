package uniandes.dpoo.hamburguesas.tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {
	
	private Pedido pedido1;
	private Pedido pedido2;
	
	@BeforeEach
    void setUp( ) throws Exception
    {
		Pedido.numeroPedidos = 0;
		pedido1 = new Pedido("Juan", "Cl. 10 Sur #39-29");
		pedido2 = new Pedido("Maria", "Ave Cra 30 #10-25");
    }
	
	@AfterEach
    void tearDown( ) throws Exception
    {
    }
	
	@Test
    void testGetIdPedido( )
    {
        assertEquals( 1, pedido1.getIdPedido(), "El id del pedido no es el esperado." );
        assertEquals( 2, pedido2.getIdPedido(), "El id del pedido no es el esperado." );
    }
	
	@Test
    void testGetNombreCliente( )
    {
        assertEquals( "Juan", pedido1.getNombreCliente(), "El nombre del cliente no es el esperado." );
    }
	
	@Test
    void testAgregarProducto( )
    {
		ProductoMenu producto1 = new ProductoMenu("corral", 14000);
		pedido1.agregarProducto(producto1);
		ArrayList<Producto> productos = pedido1.getProductos();
        assertEquals( "corral", productos.get(0).getNombre(), "El nombre del producto no se guardo correctamente." );
        assertEquals( 14000, productos.get(0).getPrecio(), "El precio del producto no se guardo correctamente." );
        ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
        ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);
        items.add(producto3);
        Combo combo = new Combo("combo corral", 0.10, items);
        pedido1.agregarProducto(combo);
        assertEquals( "combo corral", productos.get(1).getNombre(), "El nombre del combo no se guardo correctamente." );
        assertEquals( 22050, productos.get(1).getPrecio(), "El precio del combo no se guardo correctamente." );
    }
	
	@Test
    void testGetPrecioTotalPedido( )
    {
		ProductoMenu producto1 = new ProductoMenu("corral", 14000);
		ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
        ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);
        items.add(producto3);
        Combo combo = new Combo("combo corral", 0.10, items);
		pedido1.agregarProducto(producto1);
		pedido1.agregarProducto(combo);
		int precio = 0;
        for( Producto item : pedido1.getProductos())
        {
            precio += item.getPrecio( );
        }
        int iva = ( int ) ( precio * 0.19);
		int total = precio + iva;
		assertEquals( total, pedido1.getPrecioTotalPedido(), "El precio final del pedido no es el correcto." );
    }
	
	@Test
    void testGenerarTextoFactura( )
    {
		ProductoMenu producto1 = new ProductoMenu("corral", 14000);
		ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
        ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);
        items.add(producto3);
        Combo combo = new Combo("combo corral", 0.10, items);
		pedido1.agregarProducto(producto1);
		pedido1.agregarProducto(combo);
		StringBuffer sb = new StringBuffer( );

        sb.append( "Cliente: " + "Juan" + "\n" );
        sb.append( "Dirección: " + "Cl. 10 Sur #39-29" + "\n" );
        sb.append( "----------------\n" );

        for( Producto item : pedido1.getProductos() )
        {
            sb.append( item.generarTextoFactura( ) );
        }

        sb.append( "----------------\n" );
        sb.append( "Precio Neto:  " + 36050 + "\n" );
        sb.append( "IVA:          " + 6849 + "\n" );
        sb.append( "Precio Total: " + 42899 + "\n" );
        assertEquals( sb.toString(), pedido1.generarTextoFactura(), "El texto de la factura no es el esperado." );
    }
	
	@Test
    void testGuardarFactura( ) throws IOException
    {
		ProductoMenu producto1 = new ProductoMenu("corral", 14000);
		ProductoMenu producto2 = new ProductoMenu("papas medianas", 5500);
        ProductoMenu producto3 = new ProductoMenu("gaseosa", 5000);
        ArrayList<ProductoMenu> items = new ArrayList<>();
        items.add(producto1);
        items.add(producto2);
        items.add(producto3);
        Combo combo = new Combo("combo corral", 0.10, items);
		pedido1.agregarProducto(producto1);
		pedido1.agregarProducto(combo);
		
		File archivoInvalido = new File("/ruta/que/no/existe/archivo.txt");
		assertThrows(FileNotFoundException.class, () -> {
	        pedido1.guardarFactura(archivoInvalido);
	    });
		
		File archivoConRuta = new File("./facturas/FacturaTestPedido.txt"); 
		pedido1.guardarFactura(archivoConRuta);
		assertTrue(archivoConRuta.exists(), "El archivo de factura no fue creado.");
		assertTrue(archivoConRuta.length() > 0, "El archivo de factura está vacío.");
		String contenido = new String(Files.readAllBytes(archivoConRuta.toPath()));
		assertEquals(pedido1.generarTextoFactura(), contenido, "El contenido del archivo no coincide con el texto esperado.");
		
		File archivoSinRuta = File.createTempFile("facturaTest", ".txt");
		pedido1.guardarFactura(archivoSinRuta);
		String contenidoArchivo = new String(Files.readAllBytes(archivoSinRuta.toPath()));
		assertEquals(pedido1.generarTextoFactura(), contenidoArchivo, "La factura guardada no es correcta.");
    }
}
