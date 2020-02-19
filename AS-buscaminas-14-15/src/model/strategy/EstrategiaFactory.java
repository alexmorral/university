package model.strategy;

import java.util.Random;
/**
*
* @author alexmorral
*/

public class EstrategiaFactory {
		
		private static final int nombreEstrategies = 2;
	
		public EstrategiaFactory(){}
		
		/**
	     * @return Estrategia random de les que hi ha al sistema
	     */
		public static Estrategia getEstrategiaRandom() {
			Random rand = new Random();
			int r = rand.nextInt(nombreEstrategies);
			
			if(r == 1) {
				return new PuntuacioPerTemps();
			} else {
				return new PuntuacioPerNombreDeTirades();
			}
		}
		
		
		/**
	     * @return Estrategia amb nom est
	     */
		public Estrategia getEstrategia(String est) {
	    	if(est.equals("PuntuacioPerNombreDeTirades")) return new PuntuacioPerNombreDeTirades();
	    	else return new PuntuacioPerTemps();
	    }
		
}	
