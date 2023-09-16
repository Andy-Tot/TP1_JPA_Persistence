package com.utn.TP1_JPA;

import com.utn.TP1_JPA.entidades.*;
import com.utn.TP1_JPA.enumeraciones.Estado;
import com.utn.TP1_JPA.enumeraciones.FormaPago;
import com.utn.TP1_JPA.enumeraciones.Tipo;
import com.utn.TP1_JPA.enumeraciones.TipoEnvio;
import com.utn.TP1_JPA.Repositorios.ClienteRepository;
import com.utn.TP1_JPA.Repositorios.ProductoRepository;
import com.utn.TP1_JPA.Repositorios.RubroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
public class TpPersistenciaApplication {

	@Autowired
	RubroRepository rubroRepository;
	@Autowired
	ClienteRepository clienteRepository;

	public static void main(String[] args) {
		SpringApplication.run(TpPersistenciaApplication.class, args);
		System.out.println("Estoy funcionando");
	}

	@Bean
	CommandLineRunner init(RubroRepository rubroRepository1, ClienteRepository clienteRepository1){
		return args -> {
			System.out.println("----------------ESTOY FUNCIONANDO---------------------");

			// Crear instancia rubro
			Rubro rubro1 = Rubro.builder()
					.denominacion("Hamburguesas")
					.build();

			// Crear instancias de productos
			Producto producto1 = Producto.builder()
					.tiempoEstimadoCocina(60)
					.denominacion("Hamburguesa con Cheddar")
					.precioVta(2000)
					.precioCompra(1200)
					.stockActual(50)
					.stockMin(3)
					.unidadMedida("unidad1")
					.receta("receta1")
					.tipo(Tipo.Insumo)
					.build();

			Producto producto2 = Producto.builder()
					.tiempoEstimadoCocina(60)
					.denominacion("Hamburguesa con Baccon")
					.precioVta(2200)
					.precioCompra(1500)
					.stockActual(32)
					.stockMin(4)
					.unidadMedida("unidad2")
					.receta("receta2")
					.tipo(Tipo.Insumo)
					.build();

			// Agregar productos al rubro
			rubro1.agregarProducto(producto1);
			rubro1.agregarProducto(producto2);

			// Guardar rubro
			rubroRepository.save(rubro1);

			// Crear instancia Cliente
			Cliente cliente1 = Cliente.builder()
					.nombre("Facundo")
					.apellido("Sampieri")
					.mail("@mail")
					.telefono("telefono1")
					.build();

			// Crear instancia domicilio
			Domicilio domicilio1 = Domicilio.builder()
					.calle("San Martin")
					.numero(6538)
					.localidad("Lujan")
					.build();

			Domicilio domicilio2 = Domicilio.builder()
					.calle("Las Heras")
					.numero(1223)
					.localidad("Ciudad")
					.build();

			// Agregar domicilios a cliente
			cliente1.agregarDomicilio(domicilio1);
			cliente1.agregarDomicilio(domicilio2);

			// Configuracion fecha
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			String fechaString = "2023-09-09";
			Date fecha = formatoFecha.parse(fechaString);

			// Crear Instancia Pedido
			Pedido pedido1 = Pedido.builder()
					.fecha(fecha)
					.estado(Estado.Entregado)
					.tipoEnvio(TipoEnvio.Delivery)
					.build();

			Pedido pedido2 = Pedido.builder()
					.fecha(fecha)
					.estado(Estado.Entregado)
					.tipoEnvio(TipoEnvio.Delivery)
					.build();

			// Agregar productos al pedido con sus cantidades correspondientes
			pedido1.agregarProducto(producto1, 2);
			pedido1.agregarProducto(producto2, 3);
			pedido2.agregarProducto(producto1, 1);

			// Crear instancias de factura
			Factura factura1 = Factura.builder()
					.fecha(fecha)
					.total(5800)
					.numero(1)
					.dto(400)
					.formaPago(FormaPago.Efectivo)
					.build();

			Factura factura2 = Factura.builder()
					.fecha(fecha)
					.total(5400)
					.numero(2)
					.dto(600)
					.formaPago(FormaPago.Efectivo)
					.build();

			// Puedes llamar al método calcularTotal() para cada pedido si quieres calcular el total basado en los productos y sus cantidades
			pedido1.calcularTotal();
			pedido2.calcularTotal();

			// Vincular factura con pedido
			pedido1.setFactura(factura1);
			pedido2.setFactura(factura2);

			// Guardar cliente
			clienteRepository.save(cliente1);

			// Recuperar objeto rubro desde la base de datos
			Rubro rubroRecuperado = rubroRepository.findById(rubro1.getId()).orElse(null);
			if (rubroRecuperado != null) {
				System.out.println("Denominacion: " + rubroRecuperado.getDenominacion());
				rubroRecuperado.mostrarProductos();
			}

			// Recuperar Cliente desde la base de datos
			Cliente clienteRecuperado = clienteRepository.findById(cliente1.getId()).orElse(null);
			if (clienteRecuperado != null) {
				System.out.println("Nombre: " + clienteRecuperado.getNombre());
				System.out.println("Apellido: " + clienteRecuperado.getApellido());
				clienteRecuperado.mostrarDomicilios();
			}

			// Mostrar pedidos
			for (Pedido pedido : clienteRecuperado.getPedidos()) {
				System.out.println("Pedido " + pedido.getId() + ": " + pedido.getTotal());
			}
		};
	}
}
