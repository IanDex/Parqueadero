package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.json.JSONObject;

import conexion.Conexion;

public class GenericController {

	protected Conexion data = new Conexion();
	protected Connection con = null;
	protected PreparedStatement pst = null;
	
	public GenericController() {
		
	}
	
	public boolean insert(String placa) {
		boolean sucess = false;
		String consulta = "";
		int updateQuery = 0; 
    	try {
    		con = data.getConnection();

    		consulta = "INSERT INTO parqueadero (placa, salio, fechasalio, fecha, hEntrada, hSalida) "
            		+ "VALUES (?,?, now(),now(),TIME(NOW()),TIME(NOW()))";
			 pst = con.prepareStatement(consulta);
			 pst.setString(1, placa);
			 pst.setInt(2, 0);
			 updateQuery = pst.executeUpdate();
	            if (updateQuery != 0) {
	                sucess = true;
	            }
	        data.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return sucess;
	}
	
	public boolean sale(String id) {
		boolean sucess = false;
		String consulta = "";
		int updateQuery = 0; 
    	try {
    		con = data.getConnection();

    		consulta = "UPDATE parqueadero SET salio = 1, fechasalio = now(),hSalida = TIME(NOW()) WHERE parqueadero.id = ?";
			 pst = con.prepareStatement(consulta);
			 pst.setString(1, id);
			 updateQuery = pst.executeUpdate();
	            if (updateQuery != 0) {
	                sucess = true;
	            }
	        data.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return sucess;
	}
	
	public boolean check(String placa) {
		boolean sucess = false;
    	try {
    		con = data.getConnection();

    		String consulta = "select placa from parqueadero where placa = ? and salio = 0";
            pst = con.prepareStatement(consulta);
            pst.setString(1, placa);
	        ResultSet rs = pst.executeQuery();
	        while (rs.next()) {
	        	sucess = true;
	        }
	        data.closeConnection(con);
	    } catch (SQLException f) {
	        f.printStackTrace();
	    }
        return sucess;
	}
	
	public List<JSONObject> select() {
		List<JSONObject> resList = new ArrayList<JSONObject>();
		ResultSet rs = null;
		String consulta = "";
    	try {
    		con = data.getConnection();
    			consulta = "SELECT * FROM parqueadero where salio = 0";
    		
//    		System.out.println("Consulta " + consulta);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
	        ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCnt = rsMeta.getColumnCount();
			List<String> columnNames = new ArrayList<String>();
			for(int i = 1; i <= columnCnt; i++ ) {
				columnNames.add(rsMeta.getColumnName(i).toLowerCase());
			}
			while(rs.next()) {
				JSONObject obj = new JSONObject();
				for(int i = 1; i<= columnCnt; i++) {
					String key = columnNames.get(i - 1).toLowerCase();
				
					String value = rs.getString(columnNames.get(i - 1).toLowerCase());
					if(value == "" || value == null) {
						obj.put(columnNames.get(i - 1).toLowerCase(),  "-");
					}else {
						obj.put(key.toLowerCase(),  value.toLowerCase());
					}
				}
				resList.add(obj);
			}
	        data.closeConnection(con);
    	} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				System.out.println(e2);
			}
			
		}
        return resList;
	}
	
	public List<JSONObject> tabla() {
		List<JSONObject> resList = new ArrayList<JSONObject>();
		ResultSet rs = null;
		String consulta = "";
    	try {
    		con = data.getConnection();
    			consulta = "SELECT * FROM parqueadero where salio = 1";
    		
//    		System.out.println("Consulta " + consulta);
            pst = con.prepareStatement(consulta);
            rs = pst.executeQuery();
            ResultSetMetaData rsMeta = rs.getMetaData();
			int columnCnt = rsMeta.getColumnCount();
			List<String> columnNames = new ArrayList<String>();
			for(int i = 1; i <= columnCnt; i++ ) {
				columnNames.add(rsMeta.getColumnName(i).toLowerCase());
			}
			while(rs.next()) {
				JSONObject obj = new JSONObject();
			//	for(int i = 1; i<= columnCnt; i++) {
				//	String key = columnNames.get(i - 1).toLowerCase();
				obj.put("id",  rs.getString("id"));
				obj.put("placa",  rs.getString("placa"));
				obj.put("fecha",  rs.getString("fecha").toLowerCase());
				obj.put("fechasalio",  rs.getString("fechasalio").toLowerCase());
				obj.put("valor",  obtenerDiaSemana(rs.getDate("fecha"), rs.getDate("fechasalio"), rs.getTime("hSalida"), rs.getTime("hEntrada")) + " pesos");
					
				//}
				resList.add(obj);
			}
	        data.closeConnection(con);
    	} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (SQLException e2) {
				// TODO: handle exception
				System.out.println(e2);
			}
			
		}
        return resList;
	}
	
	public long obtenerDiaSemana(Date fecha,Date fSalida,Time time, Time time2){
		long valor = 0;
		System.out.println(time + "  " + time2);
		Calendar c = Calendar.getInstance();
		Calendar s = Calendar.getInstance();
		c.setTime(fecha);
		s.setTime(fSalida);
		int diaE = c.get(Calendar.DAY_OF_WEEK);
		int diaS = s.get(Calendar.DAY_OF_WEEK);
		long tiempoInicial=time2.getTime();
		long tiempoFinal=time.getTime(); 
		long resta=tiempoFinal - tiempoInicial;
		System.out.println(time2 + " Entrada " + tiempoInicial + " TF " +tiempoFinal);
		resta=resta /(1000*60);
		int cont = diaE;
		System.out.println("REsta " + resta);
		
		while(diaE != diaS+1) {
			//Esto no se hace asi Por tiempo tengo este desorden pero lo puedo optimizar mejor
			if(diaE == 2) {
				if(tiempoInicial >= 46800000 && tiempoFinal <= 86400000) {
					valor = valor + ((resta*70)/100);
				}else if(tiempoInicial <= 46800000 && tiempoFinal >= 86400000) {
					valor = valor + ((resta*40)/100);
				}
			}if(diaE == 3) {
				if(tiempoInicial >= 46800000 && tiempoFinal <= 86400000) {
					valor = valor + ((resta*70)/100);
				}else if(tiempoInicial <= 46800000 && tiempoFinal >= 86400000) {
					valor = valor + ((resta*40)/100);
				}
			}if(diaE == 4) {
				if(tiempoInicial >= 46800000 && tiempoFinal <= 86400000) {
					valor = valor + ((resta*70)/100);
				}else if(tiempoInicial <= 46800000 && tiempoFinal >= 86400000) {
					valor = valor + ((resta*40)/100);
				}
			}if(diaE == 5) {
				if(tiempoInicial >= 46800000 && tiempoFinal <= 86400000) {
					valor = valor + ((resta*70)/100);
				}if(tiempoInicial <= 46800000 && tiempoFinal >= 86400000) {
					valor = valor + ((resta*40)/100);
				}
			}if(diaE == 6) {
				if(tiempoInicial >= 46800000 && tiempoFinal <= 86400000) {
					valor = valor + ((resta*70)/100);
				}else if(tiempoInicial <= 46800000 && tiempoFinal >= 86400000) {
					valor = valor + ((resta*40)/100);
				}
			}if(diaE == 7) {
				valor = (valor + 8000);
			}if(diaE == 1) {
				valor = (valor + 8000);
			}
			diaE++;
		}
		return valor;
	    }
	
	
}
