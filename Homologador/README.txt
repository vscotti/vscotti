Homologador telefonico CEOP
---------------------------

La aplicacion consta de 2 partes: 
El modulo 1 y el modulo 2, ambos modulos tienen sus ejecutables independientabes. 
Los cuales tienen cada uno su propia configuracion.

Los ejecutables de estos procesos son: 
- firstprocess.bat para el modulo 1.
- secondprocess.bat para el modulo 2.

Ambos ejecutables fueron configurados para correr como tarea de windows y van a correr cada 5 minutos.

El margen de estos 2 ejecutables existen otros ejecutables que van a ser utilizados para mejorar el manejo
y la carga de datos en el sistema.

Estos ejecutables son:

1 - checkdatabases.bat
Este proceso permite chequear de todos los archivos puestos en el directorio databases, cuales son las loscaciones que 
todavia no se encuentran en el sistema. Genera un archivo en el directorio resultado con el nombre 
invalidlocation-{timestamp}.xls
el formato de este archivo es simple.
La primer columna es el pais, la segunda es el estado o provincia, la tercera es el small city y la cuarta el large city.
Una vez que se le agrega el prefijo este archivo puede ser utilizado por el proceso de cargar nuevas locaciones.

2 - reloadalllocations.bat
Este proceso recarga toda la base de datos, borrando los valores anteriores. Los valores son leidos de los archivos nacional.xls
e internacional.xls. Tener cuidado con estos archivos, por que si se borran y se corre el proceso limpiara las tablas
correspondientes y se prederan todos los datos.

3 - loadnewlocations.bat
Este proceso es utlizado para cargar locaciones nuevas. Utiliza el archivo data.xls. El formato de este es igual al
del proceso checkdatabases. Por ende ustede puede copiar la salida de ese proceso agregandole los prefijos
nacionales. Este proceso no borra solo agrega locaciones. chequea si la locacion ya existe y no la agrega.

4 - dataloader.bat
Recarga toda la base de datos entera. Borra toda la configuracion y la vuelve a generar con los datos por default. cuidado con
esta opcion por que borra todas las modificaciones.

5 - dumplocationdatabase.bat
Genera un archivo con todas las locaciones grabadas en la base de datos. El archivo se llamara dumplocations-{timestamp}.xls
el formato de este archivo es simple.
La primer columna es el pais, la segunda es el estado o provincia, la tercera es el small city, la cuarta el large city y
la quinta el prefijo.

ABC Consulting.

