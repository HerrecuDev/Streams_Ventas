package org.iesvdm.tests;

import static org.junit.jupiter.api.Assertions.fail;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.iesvdm.streams.Cliente;
import org.iesvdm.streams.ClienteHome;
import org.iesvdm.streams.Comercial;
import org.iesvdm.streams.ComercialHome;
import org.iesvdm.streams.Pedido;
import org.iesvdm.streams.PedidoHome;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

class StreamsTest {

	@Test
	void test() {
		fail("Not yet implemented");
	}


	@Test
	void testSkeletonCliente() {
	
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			list.forEach(System.out::println);
		
			
			//TODO STREAMS
			
		
			cliHome.commitTransaction();
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	@Test
	void testSkeletonComercial() {
	
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			list.forEach(System.out::println);		
			//TODO STREAMS
		
			comHome.commitTransaction();
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	@Test
	void testSkeletonPedido() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
			list.forEach(System.out::println);	
						
			//TODO STREAMS
		
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	/**
	 * 1. Devuelve un listado de todos los pedidos que se realizaron durante el año 2017, 
	 * cuya cantidad total sea superior a 500€.
	 * @throws ParseException 
	 */
	@Test
	void test1() throws ParseException {
		
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
			
			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2016 = sdf.parse("2016-12-31");
            Date PrimerDia2018 = sdf.parse("2018-01-01");
			
			List<Pedido> list = pedHome.findAll();
				
			List<Integer>  l = list.stream().filter(pedido -> pedido.getFecha().after(ultimoDia2016) &&  pedido.getFecha().before(PrimerDia2018) && pedido.getTotal() >= 500)
                            .map( p-> p.getId()).toList();

            l.forEach(System.out::println);
						
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 2. Devuelve un listado con los identificadores de los clientes que NO han realizado algún pedido. 
	 * 
	 */
	@Test
	void test2() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			var listado = list.stream().filter(p-> p.getPedidos().isEmpty());
            listado.forEach(System.out::println);


		
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 3. Devuelve el valor de la comisión de mayor valor que existe en la tabla comercial
	 */
	@Test
	void test3() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			
			Optional<Comercial> optionalCOmercial = list.stream().max(comparing(comercial -> comercial.getComisión()));

            optionalCOmercial.ifPresentOrElse(comerical -> System.out.println(comerical.getNombre()

                                            + " comision max "
                                            + comerical.getComisión()),
                    () -> System.out.println("no hay comerciales en la lista")

            );
				
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 4. Devuelve el identificador, nombre y primer apellido de aquellos clientes cuyo segundo apellido no es NULL. 
	 * El listado deberá estar ordenado alfabéticamente por apellidos y nombre.
	 */
	@Test
	void test4() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS

            var listado = list.stream()
                            .filter(c-> c.getApellido2() != null)
                                    .map(c -> c.getId() + " " + c.getNombre() + " " + c.getApellido1())
                                            .toList();


            listado.forEach(s -> System.out.println(s));

            Assertions.assertEquals(8, listado.size());


			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 5. Devuelve un listado con los nombres de los comerciales que terminan por "el" o "o". 
	 *  Tenga en cuenta que se deberán eliminar los nombres repetidos.
	 */
	@Test
	void test5() {
		
		ComercialHome comHome = new ComercialHome();	
		try {
			comHome.beginTransaction();
		
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS

            var listadoTerminaEn = list.stream()

                            .filter(c -> c.getNombre().endsWith("el") || c.getNombre().endsWith("o"))
                                    .map(c -> c.getNombre())
                                        .distinct()
                                            .toList();


            listadoTerminaEn.forEach(s -> System.out.println(s));

            Assertions.assertEquals(5 , listadoTerminaEn.size());



			
			comHome.commitTransaction();
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 6. Devuelve un listado de todos los clientes que realizaron un pedido durante el año 2017, cuya cantidad esté entre 300 € y 1000 €.
	 */
	@Test
	void test6() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS

            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            Date ultimoDia2016 = sdf.parse("2016-12-31");
            Date primerDia2018 = sdf.parse("2018-01-01");



            var listadoClientes = list.stream()
                            .filter(p -> p.getFecha().after(ultimoDia2016) && p.getFecha().before(primerDia2018) && p.getTotal() >= 300 && p.getTotal() <= 1000)
                                    .map(p-> p.getCliente())
                                            .toList();

            listadoClientes.forEach(s-> System.out.println(s));
		
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		} catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
	
	
	/**
	 * 7. Calcula la media del campo total de todos los pedidos realizados por el comercial Daniel Sáez
	 */
	@Test
	void test7() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
            var mediadelTotalPedidos = list.stream()
                            .filter(c -> c.getNombre().equalsIgnoreCase("Daniel") && c.getApellido1().equalsIgnoreCase("Sáez"))
                                    .flatMap(c -> (Stream<Pedido>) c.getPedidos().stream())
                                            .mapToDouble(Pedido::getTotal)
                                                    .average()
                                                            .orElse(0.0);

            //Assertions.assertEquals();

            System.out.println("La media total de todos los pedidos de este comerical es = " + mediadelTotalPedidos);



			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 8. Devuelve un listado con todos los pedidos que se han realizado.
	 *  Los pedidos deben estar ordenados por la fecha de realización
	 * , mostrando en primer lugar los pedidos más recientes
	 */
	@Test
	void test8() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS

            var pedidosOrdenados = list.stream()
                            .sorted((comparing(p -> p.getFecha(), reverseOrder())))
                                    .map(p-> p.getId() + " " + p.getFecha())
                                            .toList();
            pedidosOrdenados.forEach(s -> System.out.println(s));

            Assertions.assertEquals(16 , pedidosOrdenados.size());
			
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 9. Devuelve todos los datos de los dos pedidos de mayor valor.
	 */
	@Test
	void test9() {
	
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			//TODO STREAMS
            var datosDeLosDosPedidosMayorValor = list.stream()
                            .sorted(comparing(p -> p.getTotal() ,reverseOrder()))
                                    .map(p -> "Pedido con mayor valor con id = " + p.getId() + " tiene un valor total de = " + p.getTotal())
                                            .limit(2)
                                                    .toList();

            datosDeLosDosPedidosMayorValor.forEach(s-> System.out.println(s));

            Assertions.assertEquals(2 , datosDeLosDosPedidosMayorValor.size());
			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 10. Devuelve un listado con los identificadores de los clientes que han realizado algún pedido. 
	 * Tenga en cuenta que no debe mostrar identificadores que estén repetidos.
	 */
	@Test
	void test10() {
		
		PedidoHome pedHome = new PedidoHome();	
		try {
			pedHome.beginTransaction();
		
			List<Pedido> list = pedHome.findAll();
						
			
			//TODO STREAMS
            var listadoIdentificador = list.stream()
                            .map( p ->  p.getCliente().getId())
                                    .distinct()
                                            .toList();

            listadoIdentificador.forEach(s -> System.out.println(s));
            Assertions.assertEquals(8, listadoIdentificador.size());

			
			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 11. Devuelve un listado con el nombre y los apellidos de los comerciales que tienen una comisión entre 0.05 y 0.11.
	 * 
	 */
	@Test
	void test11() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS
            var listadoNombreComerciales = list.stream()
                            .filter(c -> c.getComisión() > 0.05 && c.getComisión() < 0.11)
                                    .map(c-> c.getNombre() + " " +  c.getApellido1())
                                            .toList();

            listadoNombreComerciales.forEach(s -> System.out.println(s));
            Assertions.assertEquals(3 , listadoNombreComerciales.size());
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 12. Devuelve el valor de la comisión de menor valor que existe para los comerciales.
	 * 
	 */
	@Test
	void test12() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS

            var comisionMenorValor = list.stream()
                            .sorted(comparing((Comercial c) -> c.getComisión()))
                                .limit(1)
                                    .map(c -> c.getComisión())
                                        .toList();

            comisionMenorValor.forEach(s -> System.out.println(s));
            Assertions.assertEquals(1, comisionMenorValor.size());

			
			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 13. Devuelve un listado de los nombres de los clientes que 
	 * empiezan por A y terminan por n y también los nombres que empiezan por P. 
	 * El listado deberá estar ordenado alfabéticamente.
	 * 
	 */
	@Test
	void test13() {
		
		ComercialHome comHome = new ComercialHome();	
		//PedidoHome pedHome = new PedidoHome();	
		try {
			//pedHome.beginTransaction();
			comHome.beginTransaction();
			
			List<Comercial> list = comHome.findAll();		
			
			//TODO STREAMS

            var listadoNombres = list.stream()
                            .flatMap( c-> (Stream<Pedido>) c.getPedidos().stream())
                                    .map(c -> c.getCliente())
                                        .sorted(comparing(c -> c.getNombre()))
                                            .filter(c -> c.getNombre().startsWith("A") && c.getNombre().endsWith("n") || c.getNombre().startsWith("P"))
                                                .distinct()  //De esta forma no se nos repite el nombre de cada cliente
                                                    .toList();



            listadoNombres.forEach( s -> System.out.println(s));
            Assertions.assertEquals(4 , listadoNombres.size());

			
			comHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 14. Devuelve un listado de los nombres de los clientes 
	 * que empiezan por A y terminan por n y también los nombres que empiezan por P. 
	 * El listado deberá estar ordenado alfabéticamente.
	 */
	@Test
	void test14() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 15. Devuelve un listado de los clientes cuyo nombre no empieza por A. 
	 * El listado deberá estar ordenado alfabéticamente por nombre y apellidos.
	 */
	@Test
	void test15() {
		
		ClienteHome cliHome = new ClienteHome();
		
		try {
			cliHome.beginTransaction();
	
			List<Cliente> list = cliHome.findAll();
			
			//TODO STREAMS
			var listadoClientes = list.stream()
                            .filter(c -> !c.getNombre().startsWith("A"))
                                .sorted(comparing((Cliente c) -> c.getNombre() + c.getApellido1() + c.getApellido2()))
                                    .map(c -> c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2())
                                            .toList();

            listadoClientes.forEach(s -> System.out.println(s));
			
			cliHome.commitTransaction();
			
		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 16. Devuelve un listado con el identificador, nombre y los apellidos de todos 
	 * los clientes que han realizado algún pedido. 
	 * El listado debe estar ordenado alfabéticamente por apellidos y nombre y se deben eliminar los elementos repetidos.
	 */
	@Test
	void test16() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();


			//TODO STREAMS

            var listadoCompleto = list.stream()
                            .map(p -> p.getCliente())
                                    .filter(c -> c.getPedidos() != null)
                                            .distinct()
                                                    .sorted(comparing((Cliente c) -> c.getApellido1())
                                                            .thenComparing((Cliente c) -> c.getApellido2() == null ? "" : c.getApellido2()))
                                                            .map(c-> c.getNombre() + " " + c.getApellido1() + " " + c.getApellido2() == null ? "" : c.getApellido2())
                                                                    .toList();

            listadoCompleto.forEach(s -> System.out.println(s));

           // Assertions.assertEquals();


			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 17. Devuelve un listado que muestre todos los pedidos que ha realizado cada cliente.
	 * El resultado debe mostrar todos los datos del cliente primero junto con un sublistado de sus pedidos.
	 * El listado debe mostrar los datos de los clientes ordenados alfabéticamente por nombre y apellidos.
	 *

	 */
	@Test
	void test17() {

		ClienteHome cliHome = new ClienteHome();

		try {
			cliHome.beginTransaction();

			List<Cliente> list = cliHome.findAll();

			//TODO STREAMS
            var listadoPedidos = list.stream()
                            .sorted(Comparator.comparing(Cliente ::getNombre)
                                    .thenComparing(Cliente ::getApellido1)
                            );





			cliHome.commitTransaction();

		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}

	}
	
	/**
	 * 18. Devuelve un listado que muestre todos los pedidos en los que ha participado un comercial. 
	 * El resultado debe mostrar todos los datos de los comerciales y el sublistado de pedidos. 
	 * El listado debe mostrar los datos de los comerciales ordenados alfabéticamente por apellidos.
	 */
	@Test
	void test18() {
		
		ComercialHome comHome = new ComercialHome();
		try {

			comHome.beginTransaction();

			List<Comercial> list = comHome.findAll();


			comHome.commitTransaction();

		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}

	}
	
	/**
	 * 19. Devuelve el nombre y los apellidos de todos los comerciales que ha participado 
	 * en algún pedido realizado por María Santana Moreno.
	 */
	@Test
	void test19() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();


			//TODO STREAMS

            var listadoComerciales = list.stream()
                            .filter(c -> c.getCliente().getId().equals(6));
                                   // .flatMap((Comercial c ) -> c.getNombre() + c.getApellido1())



			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	

	/**
	 * 20. Devuelve un listado que solamente muestre los comerciales que no han realizado ningún pedido.
	 */
	@Test
	void test20() {
		
		ComercialHome comHome = new ComercialHome();
		try {

			comHome.beginTransaction();

			List<Comercial> list = comHome.findAll();

			//TODO STREAMS
            var listadoComercialesSinPedidos = list.stream()
                            .filter(c -> c.getPedidos().isEmpty())
                                    .map(c-> "El comercial con id = " + c.getId() + " su nombre es " + c.getNombre())
                                            .toList();

            listadoComercialesSinPedidos.forEach(s-> System.out.println(s));



			comHome.commitTransaction();

		}
		catch (RuntimeException e) {
			comHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	/**
	 * 21. Calcula el número total de comerciales distintos que aparecen en la tabla pedido
	 */
	@Test
	void test21() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			//TODO STREAMS


			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 * 22. Calcula el máximo y el mínimo de total de pedido en un solo stream, transforma el pedido a un array de 2 double total, utiliza reduce junto con el array de double para calcular ambos valores.
	 */
	@Test
	void test22() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			//TODO STREAMS



			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 * 23. Calcula cuál es el valor máximo de categoría para cada una de las ciudades que aparece en cliente
	 */
	@Test
	void test23() {
		
		ClienteHome cliHome = new ClienteHome();

		try {
			cliHome.beginTransaction();

			List<Cliente> list = cliHome.findAll();

			//TODO STREAMS


			cliHome.commitTransaction();

		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 24. Calcula cuál es el máximo valor de los pedidos realizados 
	 * durante el mismo día para cada uno de los clientes. Es decir, el mismo cliente puede haber 
	 * realizado varios pedidos de diferentes cantidades el mismo día. Se pide que se calcule cuál es 
	 * el pedido de máximo valor para cada uno de los días en los que un cliente ha realizado un pedido. 
	 * Muestra el identificador del cliente, nombre, apellidos, la fecha y el valor de la cantidad.
	 * Pista: utiliza collect, groupingBy, maxBy y comparingDouble métodos estáticos de la clase Collectors
	 */
	@Test
	void test24() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			//TODO STREAMS

			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  25. Calcula cuál es el máximo valor de los pedidos realizados durante el mismo día para cada uno de los clientes, 
	 *  teniendo en cuenta que sólo queremos mostrar aquellos pedidos que superen la cantidad de 2000 €.
	 *  Pista: utiliza collect, groupingBy, filtering, maxBy y comparingDouble métodos estáticos de la clase Collectors
	 */
	@Test
	void test25() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			//TODO STREAMS


			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  26. Devuelve un listado con el identificador de cliente, nombre y apellidos 
	 *  y el número total de pedidos que ha realizado cada uno de clientes durante el año 2017.
	 * @throws ParseException 
	 */
	@Test
	void test26() throws ParseException {
		
		ClienteHome cliHome = new ClienteHome();

		try {
			cliHome.beginTransaction();

			List<Cliente> list = cliHome.findAll();
			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2016 = sdf.parse("2016-12-31");
			Date primerDia2018 = sdf.parse("2018-01-01");

			//TODO STREAMS


			cliHome.commitTransaction();

		}
		catch (RuntimeException e) {
			cliHome.rollbackTransaction();
		    throw e; // or display error message
		}
		
	}
	
	
	/**
	 * 27. Devuelve cuál ha sido el pedido de máximo valor que se ha realizado cada año. El listado debe mostrarse ordenado por año.
	 */
	@Test
	void test27() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			Calendar calendar = Calendar.getInstance();


			//TODO STREAMS


			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 *  28. Devuelve el número total de pedidos que se han realizado cada año.
	 */
	@Test
	void test28() {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			Calendar calendar = Calendar.getInstance();

			//TODO STREAMS



			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	/**
	 *  29. Devuelve los datos del cliente que realizó el pedido
	 *  
	 *   más caro en el año 2019.
	 * @throws ParseException 
	 */
	@Test
	void test29() throws ParseException {
		
		PedidoHome pedHome = new PedidoHome();
		try {
			pedHome.beginTransaction();

			List<Pedido> list = pedHome.findAll();

			//PISTA: Generación por sdf de fechas
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date ultimoDia2018 = sdf.parse("2018-12-31");
			Date primerDia2020 = sdf.parse("2020-01-01");

			//TODO STREAMS


			pedHome.commitTransaction();
		}
		catch (RuntimeException e) {
			pedHome.rollbackTransaction();
		    throw e; // or display error message
		}
	}
	
	
	/**
	 *  30. Calcula la estadísticas de total de todos los pedidos.
	 *  Pista: utiliza collect con summarizingDouble
	 */
	@Test
	void test30() throws ParseException {
		
		PedidoHome pedHome = new PedidoHome();
		try {

		}
		catch (RuntimeException e) {

		}
	}
	
}
