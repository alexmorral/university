/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.strategy;

/**
 *
 * @author alexmorral
 */
public class PuntuacioPerNombreDeTirades extends Estrategia{
	
	public PuntuacioPerNombreDeTirades(){}
	
	@Override
	/**
     * @return "PuntuacioPerNombreDeTirades"
     */
	public String getNom() {
		return "PuntuacioPerNombreDeTirades";
	}
    
	@Override
    public int getPuntuacioPrincipiant() {
		return 100;
	}
    @Override
    public int getPuntuacioExpert() {
    	return 500;
    }
    @Override
    public int getPuntuacioIntermedi() {
    	return 300;
    }
}
