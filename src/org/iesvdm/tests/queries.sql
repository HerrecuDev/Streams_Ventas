
	 -- 2. Devuelve un listado con los identificadores de los clientes que NO han realizado algún pedido.
	 select * from cliente C left join pedido P on  C.id = P.id_Cliente Where P.id is null;


    select * from cliente C where C.id not in (select)