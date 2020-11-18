/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 *******************************************************************************/
package it.csi.sicee.siceegwsiape.util;

//import it.csi.sicee.siceegwapelib.jaxb.Ape2015;
//import it.csi.sicee.siceegwapelib.jaxb.TypeDatiAttestato;
//import it.csi.sicee.siceegwapelib.jaxb.TypeDatiGenerali;
import it.csi.sicee.siceegwsiape.integration.db.SiceeRCertifServener2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeRCombDener2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTAltreInfo;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificato;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificatoPK;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTCertificatore;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTDatiEner2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTDatiGenerali;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTDatiXml2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTDaticatastSec;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTDetImp2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTQtaConsumi2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTRaccomand2015;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiape;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiapeLog;
import it.csi.sicee.siceegwsiape.integration.db.SiceeTSiapePK;
import it.csi.sicee.siceegwsiape.jaxb.lib.Ape2015;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeClasseEnergetica;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeConsumoImpianto;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeConsumoImpiantoAltro;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiAttestato;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiCatastali;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiEnergetici;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiExtra;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiFabbricato;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiGenerali;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiGenerali.DatiIdentificativi;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiGenerali.DatiIdentificativi.Catasto;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiGenerali.ServiziEnergeticiPresenti;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.ClimatizzazioneEstiva;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.ClimatizzazioneInvernale;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.Illuminazione;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.ImpiantiCombinati;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.ProduzioneACS;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.ProduzioneFontiRinnovabili;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.TrasportoPersoneCose;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeDatiImpianti.VentilazioneMeccanica;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeImpianto;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeImpianto.VettoriEnergeticiUtilizzati;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeInfo;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeInterventoRaccomandato;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazione;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazioneGlobale;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazioneGlobale.PrestazioneEnergeticaFabbricato;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazioneGlobale.PrestazioneEnergeticaGlobale;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazioneGlobale.Riferimenti;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypePrestazioneImpianti;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeRaccomandazioni;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeSoftwareUtilizzato;
import it.csi.sicee.siceegwsiape.jaxb.lib.TypeSoggettoCertificatore;
import it.csi.sicee.siceegwsiape.util.enea.siape.exception.SiapeException;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.ApeXmlParser;
import it.csi.sicee.siceegwsiape.util.enea.siape.utils.CodeMessage;

//import it.csi.sicee.siceegwapelib.jaxb.Ape2015;
//import it.enea.siape.Ape2015;
//import it.enea.siape.TypeClasseEnergetica;
//import it.enea.siape.TypeConsumoImpianto;
//import it.enea.siape.TypeConsumoImpiantoAltro;
//import it.enea.siape.TypeDatiAttestato;
//import it.enea.siape.TypeDatiCatastali;
//import it.enea.siape.TypeDatiEnergetici;
//import it.enea.siape.TypeDatiExtra;
//import it.enea.siape.TypeDatiFabbricato;
//import it.enea.siape.TypeDatiGenerali;
//import it.enea.siape.TypeDatiGenerali.DatiIdentificativi;
//import it.enea.siape.TypeDatiGenerali.DatiIdentificativi.Catasto;
//import it.enea.siape.TypeDatiGenerali.ServiziEnergeticiPresenti;
//import it.enea.siape.TypeDatiImpianti;
//import it.enea.siape.TypeDatiImpianti.ClimatizzazioneEstiva;
//import it.enea.siape.TypeDatiImpianti.ClimatizzazioneInvernale;
//import it.enea.siape.TypeDatiImpianti.Illuminazione;
//import it.enea.siape.TypeDatiImpianti.ImpiantiCombinati;
//import it.enea.siape.TypeDatiImpianti.ProduzioneACS;
//import it.enea.siape.TypeDatiImpianti.ProduzioneFontiRinnovabili;
//import it.enea.siape.TypeDatiImpianti.TrasportoPersoneCose;
//import it.enea.siape.TypeDatiImpianti.VentilazioneMeccanica;
//import it.enea.siape.TypeImpianto;
//import it.enea.siape.TypeImpianto.VettoriEnergeticiUtilizzati;
//import it.enea.siape.TypeInterventoRaccomandato;
//import it.enea.siape.TypePrestazione;
//import it.enea.siape.TypePrestazioneGlobale;
//import it.enea.siape.TypePrestazioneGlobale.PrestazioneEnergeticaFabbricato;
//import it.enea.siape.TypePrestazioneGlobale.PrestazioneEnergeticaGlobale;
//import it.enea.siape.TypePrestazioneGlobale.Riferimenti;
//import it.enea.siape.TypePrestazioneImpianti;
//import it.enea.siape.TypeRaccomandazioni;
//import it.enea.siape.TypeSoftwareUtilizzato;
//import it.enea.siape.TypeSoggettoCertificatore;

import java.io.File;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;



//import org.apache.cxf.wsdl.TDefinitions;
import org.apache.log4j.Logger;

public class MapDto {
	private static Logger logger = Logger.getLogger(APEConstants.LOGGER_PREFIX);


	public static SiceeTCertificatoPK mapToCertificatoPK(SiceeTSiapePK siapePK)
	{
		SiceeTCertificatoPK certificatoPK = new SiceeTCertificatoPK();

		certificatoPK.setIdCertificatore(siapePK.getIdCertificatore());
		certificatoPK.setProgrCertificato(siapePK.getProgrCertificato());
		certificatoPK.setAnno(siapePK.getAnno());



		return certificatoPK;
	}
	
	public static Ape2015 mapToApe2015(SiceeTCertificato tCertificato)
	{
		String codiceIdentificativo = tCertificato.getId().getAnno() + " " + tCertificato.getSiceeTCertificatore().getNumCertificatore() + " " + tCertificato.getId().getProgrCertificato();
		
		String xml = null;
		
		Ape2015 ape2015 = new Ape2015();

		// Info
		TypeInfo info = new TypeInfo();
		info.setCopyright(APEConstants.INFO_COPYRIGHT);
		info.setVersione(APEConstants.INFO_VERSIONE);
		ape2015.setInfo(info);
		
		// Dati Attestato
		TypeDatiAttestato datiAttestato = new TypeDatiAttestato();
		datiAttestato.setCodiceISTATRegione(APEConstants.COD_ISTAT_PIEMONTE);
		datiAttestato.setCodiceIdentificativo(codiceIdentificativo);
		datiAttestato.setDataScadenza(GenericUtil.convertToXMLGregorianCalendar(tCertificato.getDtScadenza()));
		ape2015.setDatiAttestato(datiAttestato);
		
		// Dati Generali
		SiceeTDatiGenerali tDatiGenerali = (SiceeTDatiGenerali) tCertificato.getSiceeTDatiGeneralis().toArray()[0];
		SiceeTAltreInfo tAltreInfo = (SiceeTAltreInfo) tCertificato.getSiceeTAltreInfos().toArray()[0];
		SiceeTDatiEner2015 tDatiEner2015 = (SiceeTDatiEner2015) tCertificato.getSiceeTDatiEner2015s().toArray()[0];
		Set<SiceeTDaticatastSec> tDaticatastSecs = tCertificato.getSiceeTDaticatastSecs();
		Set<SiceeTQtaConsumi2015> tQtaConsumi2015s = tCertificato.getSiceeTQtaConsumi2015s();
		Set<SiceeTRaccomand2015> tRaccomand2015s = tCertificato.getSiceeTRaccomand2015s();
		SiceeRCombDener2015 rCombDener2015 = (SiceeRCombDener2015) tDatiEner2015.getSiceeRCombDener2015s().toArray()[0];
		Set<SiceeRCertifServener2015> rCertifServener2015s = tCertificato.getSiceeRCertifServener2015s();
		SiceeTCertificatore tCertificatore = tCertificato.getSiceeTCertificatore();
		SiceeTDatiXml2015 tDatiXml2015 = tCertificato.getSiceeTDatiXml2015();
		
		TypeDatiGenerali datiGenerali = new TypeDatiGenerali();
		datiGenerali.setDestinazioneUso(tDatiGenerali.getSiceeDDestUso2015().getFlgResidenziale().equals("S")?GenericUtil.convertToShort("0"):GenericUtil.convertToShort("1"));
		datiGenerali.setClassificazioneDPR412(GenericUtil.convertToShort(tDatiGenerali.getSiceeDDestUso2015().getIdDestUso2015()));
		datiGenerali.setOggettoAttestato(GenericUtil.convertToShort(tDatiGenerali.getFkOggettoApe()));
		datiGenerali.setNumeroUnitaImmobiliari(GenericUtil.convertToShort(tDatiGenerali.getNrUm()));
		
		// Devo sottrarre 100 all'fk
		datiGenerali.setMotivazione(GenericUtil.convertToMotivazioneSiape(tAltreInfo.getFkMotivo()));

		if (tAltreInfo.getFkMotivo().intValue() == 105)
		{
			datiGenerali.setAltraMotivazione(true);
			datiGenerali.setDescrizioneAltraMotivazione(tAltreInfo.getMotivoAltro());
		}
		else
		{
			datiGenerali.setAltraMotivazione(false);
		}
		
		DatiIdentificativi datiIdentificativi = new DatiIdentificativi();
		datiIdentificativi.setFotoEdificio(null); // Per ora non la passerei - si vuole solo JPG ma noi gestiamo anche altri formati: gif e png
		datiIdentificativi.setCodiceISTAT(tCertificato.getIdComune());
		
		String indirizzo = tCertificato.getDescIndirizzo();
		if (!GenericUtil.isNullOrEmpty(tCertificato.getNumCivico()))
		{
			indirizzo += " " + tCertificato.getNumCivico();
		}
		datiIdentificativi.setIndirizzo(indirizzo);

		datiIdentificativi.setPiano(GenericUtil.convertToValidString(tCertificato.getPiano()));
		datiIdentificativi.setInterno(GenericUtil.convertToValidString(tCertificato.getInterno()));
		datiIdentificativi.setLatitudineGIS(tCertificato.getCoordN());
		datiIdentificativi.setLatitudineGIS(tCertificato.getCoordN());
		datiIdentificativi.setLongitudineGIS(tCertificato.getCoordE());
		datiIdentificativi.setZonaClimatica(tDatiGenerali.getZonaClimatica());
		datiIdentificativi.setAnnoCostruzione(tDatiGenerali.getAnnoCostruzione());
		datiIdentificativi.setSuperficieUtileRiscaldata(tDatiGenerali.getSupRiscaldata());
		datiIdentificativi.setSuperficieUtileRaffrescata(tDatiGenerali.getSupRaffrescata());
		datiIdentificativi.setVolumeLordoRiscaldato(tDatiGenerali.getVolLordoRiscaldato());
		datiIdentificativi.setVolumeLordoRaffrescato(tDatiGenerali.getVolLordoRaffrescato());
		
		Catasto catasto = new Catasto();
		Short tipoCatasto = null;
		TypeDatiCatastali datiCatasto = new TypeDatiCatastali();
		datiCatasto.setCodiceCatastale(tCertificato.getCodiceComuneCatastale());
		datiCatasto.setComuneCatastale(tCertificato.getDescComune());
		
		if (!GenericUtil.isNullOrEmpty(tCertificato.getRifCatasto()))
		{
			if (tCertificato.getRifCatasto().equalsIgnoreCase(APEConstants.DESC_RIF_CATASTO_NCT))
			{
				tipoCatasto = APEConstants.ID_RIF_CATASTO_NCT;
			} 
			else if (tCertificato.getRifCatasto().equalsIgnoreCase(APEConstants.DESC_RIF_CATASTO_NCEU))
			{
				tipoCatasto = APEConstants.ID_RIF_CATASTO_NCEU;
			}
			else if (tCertificato.getRifCatasto().equalsIgnoreCase(APEConstants.DESC_RIF_CATASTO_CT))
			{
				tipoCatasto = APEConstants.ID_RIF_CATASTO_CT;
			}
			
			datiCatasto.setTipoCatasto(tipoCatasto);
		}
		
		datiCatasto.setSezione(tCertificato.getSezione());
		datiCatasto.setFoglio(tCertificato.getFoglio());
		datiCatasto.setParticella(tCertificato.getParticella());
		
		// E' normale che non ci sia neanche un subalterno??? 
		if (!GenericUtil.isNullOrEmpty(tCertificato.getSubalterno()))
		{
			datiCatasto.getAltriSubalterni().add(tCertificato.getSubalterno());
		}
		
		catasto.getDatiCatastali().add(datiCatasto);

		// questa la uso come chiave di rottura
		String chiaveCatastoOld = tCertificato.getCodiceComuneCatastale()+tCertificato.getSezione()+tCertificato.getFoglio()+tCertificato.getParticella();

		//System.out.println("chiaveCatastoOld: "+chiaveCatastoOld);
		
		
		int elemPerRiga = 1;
		boolean isFirst = true; 
		
		//System.out.println("tDaticatastSecs: "+tDaticatastSecs);
		//System.out.println("tDaticatastSecs.size(): "+tDaticatastSecs.size());
		
		if (tDaticatastSecs != null && tDaticatastSecs.size() > 0)
		{
			String chiaveCatastoNew = null;
		
			
			for (SiceeTDaticatastSec datiCatastali : tDaticatastSecs) {

				// mi creo la chiave da confrontare
				chiaveCatastoNew = datiCatastali.getCodiceComuneCatastale()+datiCatastali.getSezione()+datiCatastali.getFoglio()+datiCatastali.getParticella();
				
				//System.out.println("chiaveCatastoNew: "+chiaveCatastoNew);

				if (!chiaveCatastoOld.equalsIgnoreCase(chiaveCatastoNew) || elemPerRiga == APEConstants.MAX_ALTRI_SUBALTERNI)
				{
			
					if (!isFirst)
					{
						// Nel caso sia il primo elemnto della lista, non devo aggiungere il datoCatatsto, perche' altrimenti aggiungerei un'altra volta i dati catastali generali (fuori dal for)
						// Devo settare i dati
						catasto.getDatiCatastali().add(datiCatasto);
					}
					
					// Ripulisco l'oggetto
					datiCatasto = new TypeDatiCatastali();
					
					datiCatasto.setCodiceCatastale(datiCatastali.getCodiceComuneCatastale());
					datiCatasto.setComuneCatastale(datiCatastali.getDescComune());
					datiCatasto.setTipoCatasto(tipoCatasto);
					datiCatasto.setSezione(datiCatastali.getSezione());
					datiCatasto.setFoglio(datiCatastali.getFoglio());
					datiCatasto.setParticella(datiCatastali.getParticella());

					// Devo ripartire dal primo elemento
					elemPerRiga = 0;
					isFirst = false;
					
					// Setto la nuova chiave OLD 
					chiaveCatastoOld = chiaveCatastoNew;
				}

				if (!GenericUtil.isNullOrEmpty(datiCatastali.getSubalterno()))
				{
					datiCatasto.getAltriSubalterni().add(datiCatastali.getSubalterno());
				}
				
				elemPerRiga++;
			
			}
			
			// Setto l'ultimo
			//datiCatasto.getAltriSubalterni().add(tCertificato.getSubalterno());
			catasto.getDatiCatastali().add(datiCatasto);

		}
		
		datiIdentificativi.setCatasto(catasto);

		datiGenerali.setDatiIdentificativi(datiIdentificativi);

		ServiziEnergeticiPresenti serEnerPresenti = new ServiziEnergeticiPresenti();
		serEnerPresenti.setClimatizzazioneInvernale(GenericUtil.convertToBoolean(tDatiEner2015.getFlgClimatInvernale()));
		serEnerPresenti.setClimatizzazioneEstiva(GenericUtil.convertToBoolean(tDatiEner2015.getFlgClimatEstiva()));
		serEnerPresenti.setVentilazioneMeccanica(GenericUtil.convertToBoolean(tDatiEner2015.getFlgVentilMeccanica()));
		serEnerPresenti.setProduzioneAcquaCaldaSanitaria(GenericUtil.convertToBoolean(tDatiEner2015.getFlgProdH2oSanitaria()));
		serEnerPresenti.setIlluminazione(GenericUtil.convertToBoolean(tDatiEner2015.getFlgIlluminazione()));
		serEnerPresenti.setTrasportoPersoneCose(GenericUtil.convertToBoolean(tDatiEner2015.getFlgTrasportoPersCose()));
		
		datiGenerali.setServiziEnergeticiPresenti(serEnerPresenti);
		ape2015.setDatiGenerali(datiGenerali);

		TypePrestazioneGlobale prestGlobale = new TypePrestazioneGlobale();
		
		PrestazioneEnergeticaFabbricato prestEnerFabbricato = new PrestazioneEnergeticaFabbricato();
		prestEnerFabbricato.setInverno(GenericUtil.convertToShort(tDatiEner2015.getFlgSmileInverno()));
		prestEnerFabbricato.setEstate(GenericUtil.convertToShort(tDatiEner2015.getFlgSmileEstate()));
		prestGlobale.setPrestazioneEnergeticaFabbricato(prestEnerFabbricato);

		PrestazioneEnergeticaGlobale prestEnerGlobale = new PrestazioneEnergeticaGlobale();
		prestEnerGlobale.setEdificioEnergiaQuasiZero(GenericUtil.convertToBoolean(tDatiEner2015.getFlgEdifE0()));
		
		TypeClasseEnergetica classeEnergetica = new TypeClasseEnergetica();
		if (tDatiEner2015.getSiceeDClasseEnergetica1() != null)
		{		
			classeEnergetica.setClasseEnergetica(tDatiEner2015.getSiceeDClasseEnergetica1().getDescr());
			classeEnergetica.setEpglnren(tDatiEner2015.getEpglNrenGlobale());
			prestEnerGlobale.setClassificazione(classeEnergetica);
			prestGlobale.setPrestazioneEnergeticaGlobale(prestEnerGlobale);
		}
		
		Riferimenti riferimenti = new Riferimenti();
		// RIUTILIZZO LO STESSO OGGETTO - da testare
		if (tDatiEner2015.getSiceeDClasseEnergetica2() != null)
		{		
			classeEnergetica = new TypeClasseEnergetica();
			classeEnergetica.setClasseEnergetica(tDatiEner2015.getSiceeDClasseEnergetica2().getDescr());
			classeEnergetica.setEpglnren(tDatiEner2015.getEpglNrenNuovi());
			riferimenti.setClassificazioneNuovi(classeEnergetica);
		}
		
		// RIUTILIZZO LO STESSO OGGETTO - da testare
		if (tDatiEner2015.getSiceeDClasseEnergetica3() != null)
		{
			classeEnergetica = new TypeClasseEnergetica();
			classeEnergetica.setClasseEnergetica(tDatiEner2015.getSiceeDClasseEnergetica3().getDescr());
			classeEnergetica.setEpglnren(tDatiEner2015.getEpglNrenEsistenti());
			riferimenti.setClassificazioneEsistenti(classeEnergetica);
		}
		
		prestGlobale.setRiferimenti(riferimenti);
		
		ape2015.setPrestazioneGlobale(prestGlobale);
		
		
		TypePrestazioneImpianti prestImpianti = new TypePrestazioneImpianti();
		int idCombustibile;
		TypeConsumoImpianto consumoImpianto = null;
		for (SiceeTQtaConsumi2015 siceeTQtaConsumi2015 : tQtaConsumi2015s) {

			idCombustibile = GenericUtil.convertToInteger(siceeTQtaConsumi2015.getFkCombustibile());
			
			consumoImpianto = new TypeConsumoImpianto(); 
			// il consumo annuo lo arrotondo senza numeri decimali (come da specifiche xsd ENEA)
			consumoImpianto.setConsumoAnnuo(GenericUtil.convertToScaleBigDecimal(0, siceeTQtaConsumi2015.getQuantita()));
			consumoImpianto.setConsumoAnnuoUM(GenericUtil.convertToShort(siceeTQtaConsumi2015.getFkUnitaMisura()));
			
			//prestImpianti.setEnergiaElettricaRete(value)
			
			switch (idCombustibile) {
			case APEConstants.ID_COMBUSTIBILE_ENERGIAELETTRICA:

				prestImpianti.setEnergiaElettricaRete(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_GASNATURALE:

				prestImpianti.setGasNaturale(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_GPL:

				prestImpianti.setGpl(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_CARBONE:

				prestImpianti.setCarbone(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_GASOL:

				prestImpianti.setGasolio(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_OLIO_COMBUSTIBILE:

				prestImpianti.setOlioCombustibile(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_BIOMASSESOLIDE:

				prestImpianti.setBiomasseSolide(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_BIOMASSELIQUIDE:

				prestImpianti.setBiomasseLiquide(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_BIOMASSEGASSOSE:

				prestImpianti.setBiomasseGassose(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_SOLAREFOTOVOLTAICO:

				prestImpianti.setSolareFotovoltaico(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_SOLARETERMICO:

				prestImpianti.setSolareTermico(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_EOLICO:

				prestImpianti.setEolico(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_TELERISCALDAMENTO:

				prestImpianti.setTeleriscaldamento(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_TELERAFFRESCAMENTO:

				prestImpianti.setTeleraffrescamento(consumoImpianto);

				break;
			case APEConstants.ID_COMBUSTIBILE_ALTRO:

				TypeConsumoImpiantoAltro consumoImpiantoAltro = new TypeConsumoImpiantoAltro();
				consumoImpiantoAltro.setConsumoAnnuo(GenericUtil.convertToScaleBigDecimal(0, siceeTQtaConsumi2015.getQuantita()));
				consumoImpiantoAltro.setConsumoAnnuoUM(GenericUtil.convertToShort(siceeTQtaConsumi2015.getFkUnitaMisura()));
				consumoImpiantoAltro.setDescrizioneFonteEnergetica(GenericUtil.convertToValidString(siceeTQtaConsumi2015.getAltroDescrComb()));
				prestImpianti.setAltro(consumoImpiantoAltro);

				break;
			}
			
			
		}
		
		prestImpianti.setEpglnren(tDatiEner2015.getEpglNrenGlobale());
		prestImpianti.setEpglren(tDatiEner2015.getEpglRenGlobale());
		prestImpianti.setEmissioniCO2(tDatiEner2015.getEmissioniCo2());
		
		ape2015.setPrestazioneImpianti(prestImpianti);
		
		
		TypeRaccomandazioni raccomandazioni = new TypeRaccomandazioni();
		TypeInterventoRaccomandato intRaccomandato = null;
		
		for (SiceeTRaccomand2015 siceeTRaccomand2015 : tRaccomand2015s) {
			intRaccomandato = new TypeInterventoRaccomandato();
			intRaccomandato.setCodice(GenericUtil.convertToShort(siceeTRaccomand2015.getSiceeDRiqEner2015().getIdXml()));
			intRaccomandato.setTipoInterventoRaccomandato(GenericUtil.convertToValidString(siceeTRaccomand2015.getTipoIntervento()));
			intRaccomandato.setRistrutturazioneImportante(GenericUtil.convertToBoolean(siceeTRaccomand2015.getFlgRistrutturazione()));
			intRaccomandato.setTempoRitornoInvestimento(siceeTRaccomand2015.getNrAnniRitInvest());
			
			// RIUTILIZZO LO STESSO OGGETTO - da testare
			classeEnergetica = new TypeClasseEnergetica();
			classeEnergetica.setClasseEnergetica(siceeTRaccomand2015.getSiceeDClasseEnergetica().getDescr());
			classeEnergetica.setEpglnren(siceeTRaccomand2015.getEpglNrenSingoloInt());
			intRaccomandato.setClassificazioneRaggiungibile(classeEnergetica);
			
			raccomandazioni.getInterventoRaccomandato().add(intRaccomandato);
		}

		// RIUTILIZZO LO STESSO OGGETTO - da testare
		if (tDatiEner2015.getSiceeDClasseEnergetica4() != null)
		{
			classeEnergetica = new TypeClasseEnergetica();
			classeEnergetica.setClasseEnergetica(tDatiEner2015.getSiceeDClasseEnergetica4().getDescr());
			classeEnergetica.setEpglnren(tDatiEner2015.getEpglNrenRaccTotale());

			raccomandazioni.setClassificazione(classeEnergetica);
		}
		
		ape2015.setRaccomandazioni(raccomandazioni);

		TypeDatiEnergetici datiEnergetici = new TypeDatiEnergetici();
		
		datiEnergetici.setEnergiaEsportata(GenericUtil.convertToScaleBigDecimal(0, tDatiEner2015.getEnergiaEsportata()));
		datiEnergetici.setVettoreEnergetico(GenericUtil.convertToShort(rCombDener2015.getSiceeDCombustibile().getCodXml()));
		
		ape2015.setDatiEnergetici(datiEnergetici);
		
		TypeDatiFabbricato datiFabbricato = new TypeDatiFabbricato();
		datiFabbricato.setVolumeRiscaldato(tDatiGenerali.getVolLordoRiscaldato());
		datiFabbricato.setSuperficieDisperdente(tDatiGenerali.getSupDisperdente());
		datiFabbricato.setRapportoSV(tDatiEner2015.getRapportoSv());
		datiFabbricato.setEphnd(tDatiEner2015.getEph());
		datiFabbricato.setRapportoAsolAsupUtile(tDatiEner2015.getAsolAsup());
		datiFabbricato.setYie(tDatiEner2015.getYie());

		ape2015.setDatiFabbricato(datiFabbricato);
		
		TypeDatiImpianti datiImpianti = new TypeDatiImpianti();
		
		Set<SiceeTDetImp2015> tSiceeTDetImp2015s = null; 
		//SiceeTDetImp2015 tDetImp2015 = null;
		TypeImpianto impianto = null;
		TypePrestazione prestazione = null;
		
		ClimatizzazioneInvernale climaInvernale = new ClimatizzazioneInvernale();
		ClimatizzazioneEstiva climaEstiva = new ClimatizzazioneEstiva();
		ProduzioneACS produzioneACS = new ProduzioneACS();
		ImpiantiCombinati impiantiCombinati = new ImpiantiCombinati();
		ProduzioneFontiRinnovabili prodFontiRinnovabili = new ProduzioneFontiRinnovabili();
		VentilazioneMeccanica ventilazioneMeccanica = new VentilazioneMeccanica();
		Illuminazione illuminazione = new Illuminazione();
		TrasportoPersoneCose trasportoPersoneCose = new TrasportoPersoneCose();
		
		// DA VERIFICARE
		for (SiceeRCertifServener2015 siceeRCertifServener2015 : rCertifServener2015s) {

			//tDetImp2015 = (SiceeTDetImp2015) siceeRCertifServener2015.getSiceeTDetImp2015s().toArray()[0];

			tSiceeTDetImp2015s = siceeRCertifServener2015.getSiceeTDetImp2015s();

			for (SiceeTDetImp2015 tDetImp2015 : tSiceeTDetImp2015s) {

				GenericUtil.stampa(tDetImp2015, false, 3);

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_CLIMAINVER)
				{

					if (tDetImp2015.getTipoImpianto() != null && tDetImp2015.getTipoImpianto().equalsIgnoreCase(APEConstants.DESC_IMPIANTO_SIMULATO))
					{
						climaInvernale.setImpiantoSimulato(APEConstants.DESC_IMPIANTO_SIMULATO);
					}
					else
					{
						impianto = new TypeImpianto();
						impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
						impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
						impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

						logger.info("STAMPO SEZIONE_CLIMAINVER - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
						logger.info("STAMPO SEZIONE_CLIMAINVER - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

						impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

						logger.info("STAMPO SEZIONE_CLIMAINVER - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");


						VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
						vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
						impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
						impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

						climaInvernale.getImpianto().add(impianto);
					}

					climaInvernale.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					climaInvernale.setPrestazione(prestazione);

					datiImpianti.setClimatizzazioneInvernale(climaInvernale);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_CLIMAEST)
				{


					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_CLIMAEST - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_CLIMAEST - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_CLIMAEST - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");


					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					climaEstiva.getImpianto().add(impianto);

					climaEstiva.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					climaEstiva.setPrestazione(prestazione);

					datiImpianti.setClimatizzazioneEstiva(climaEstiva);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_ACQUACALDA)
				{
					if (tDetImp2015.getTipoImpianto() != null && tDetImp2015.getTipoImpianto().equalsIgnoreCase(APEConstants.DESC_IMPIANTO_SIMULATO))
					{
						produzioneACS.setImpiantoSimulato(APEConstants.DESC_IMPIANTO_SIMULATO);
					}
					else
					{
						impianto = new TypeImpianto();
						impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
						impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
						impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

						logger.info("STAMPO SEZIONE_ACQUACALDA - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
						logger.info("STAMPO SEZIONE_ACQUACALDA - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));


						impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

						logger.info("STAMPO SEZIONE_ACQUACALDA - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

						VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
						vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
						impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
						impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

						produzioneACS.getImpianto().add(impianto);
					}

					produzioneACS.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					produzioneACS.setPrestazione(prestazione);

					datiImpianti.setProduzioneACS(produzioneACS);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_IMPIANTICOMBINATI)
				{

					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_IMPIANTICOMBINATI - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_IMPIANTICOMBINATI - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_IMPIANTICOMBINATI - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					impiantiCombinati.getImpianto().add(impianto);

					impiantiCombinati.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					
					if (siceeRCertifServener2015.getEpren() != null || siceeRCertifServener2015.getEpnren() != null)
					{
						prestazione = new TypePrestazione();
						prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
						prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
						impiantiCombinati.setPrestazione(prestazione);
					}
					
					datiImpianti.setImpiantiCombinati(impiantiCombinati);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_PRODFONTIRINN)
				{

					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_PRODFONTIRINN - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_PRODFONTIRINN - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_PRODFONTIRINN - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					prodFontiRinnovabili.getImpianto().add(impianto);

					datiImpianti.setProduzioneFontiRinnovabili(prodFontiRinnovabili);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_VENTMECC)
				{

					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_VENTMECC - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_VENTMECC - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_VENTMECC - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					ventilazioneMeccanica.getImpianto().add(impianto);

					ventilazioneMeccanica.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					ventilazioneMeccanica.setPrestazione(prestazione);

					datiImpianti.setVentilazioneMeccanica(ventilazioneMeccanica);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_ILLUMINAZIONE)
				{

					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_ILLUMINAZIONE - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_ILLUMINAZIONE - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_ILLUMINAZIONE - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					illuminazione.getImpianto().add(impianto);

					illuminazione.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					illuminazione.setPrestazione(prestazione);

					datiImpianti.setIlluminazione(illuminazione);
				}

				if (siceeRCertifServener2015.getId().getIdServEner() == APEConstants.SEZIONE_TRASPORTO)
				{

					impianto = new TypeImpianto();
					impianto.setTipoImpianto(GenericUtil.convertToShort(tDetImp2015.getFkTipoImpianto()));
					impianto.setDescrizioneImpianto(tDetImp2015.getTipoImpianto());
					impianto.setAnnoInstallazione(tDetImp2015.getAnnoInstall());

					logger.info("STAMPO SEZIONE_TRASPORTO - CodiceImpiantoCit: "+tDetImp2015.getCodiceImpiantoCit());
					logger.info("STAMPO SEZIONE_TRASPORTO - convertToString(CodiceImpiantoCit: "+GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					impianto.setCodiceCatastoRegionaleImpiantiTermici(GenericUtil.convertToValidString(tDetImp2015.getCodiceImpiantoCit()));

					logger.info("STAMPO SEZIONE_TRASPORTO - impianto.getCodiceCatastoRegionaleImpiantiTermici(): *"+impianto.getCodiceCatastoRegionaleImpiantiTermici()+"*");

					VettoriEnergeticiUtilizzati vettoriEnergetici = new VettoriEnergeticiUtilizzati();
					vettoriEnergetici.getVettore().add(GenericUtil.convertToCombustibileSiape(tDetImp2015.getFkCombustibile()));
					impianto.setVettoriEnergeticiUtilizzati(vettoriEnergetici);
					impianto.setPotenzaNominale(GenericUtil.convertTo2BigDecimal(tDetImp2015.getPotenzaNominKw()));

					trasportoPersoneCose.getImpianto().add(impianto);

					trasportoPersoneCose.setEfficienza(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEfficienza()));
					prestazione = new TypePrestazione();
					prestazione.setEpren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpren()));
					prestazione.setEpnren(GenericUtil.convertTo2BigDecimal(siceeRCertifServener2015.getEpnren()));
					trasportoPersoneCose.setPrestazione(prestazione);

					datiImpianti.setTrasportoPersoneCose(trasportoPersoneCose);
				}
			}
		}
		
		ape2015.setDatiImpianti(datiImpianti);
		
		ape2015.setInformazioniMiglioramento(GenericUtil.convertToValidString(tAltreInfo.getInfoMiglPrestEnerg()));
		
		TypeSoggettoCertificatore soggettoCertificatore = new TypeSoggettoCertificatore();
		soggettoCertificatore.setTipoSoggettoCertificatore(GenericUtil.convertToShort(tAltreInfo.getFkRuoloCert()));
		
		soggettoCertificatore.setNome(tCertificatore.getNome());
		soggettoCertificatore.setCognome(tCertificatore.getCognome());
		soggettoCertificatore.setDenominazione(tCertificatore.getCognome());
		
		String indirizzoCert = tCertificatore.getDescIndirizzo();
		if (!GenericUtil.isNullOrEmpty(tCertificatore.getNumCivicoResidenza()))
		{
			indirizzoCert += " " + tCertificatore.getNumCivicoResidenza();
		}
		
		if (!GenericUtil.isNullOrEmpty(tCertificatore.getDescComuneResidenza()))
		{
			indirizzoCert += " " + tCertificatore.getDescComuneResidenza();
		}
		
		soggettoCertificatore.setIndirizzo(indirizzoCert);
		soggettoCertificatore.setEmail(tCertificatore.getEmail());
		soggettoCertificatore.setTelefono(tCertificatore.getTelefono());
		
		soggettoCertificatore.setTitolo(tCertificatore.getSiceeDIstruzione().getDenominazione());
		
		String ordineIscrizione = "";
		
		if (!GenericUtil.isNullOrEmpty(tCertificatore.getOrdine()))
		{
			ordineIscrizione = tCertificatore.getOrdine() + " ";
		}
		
		if (!GenericUtil.isNullOrEmpty(tCertificatore.getSettore()))
		{
			ordineIscrizione += tCertificatore.getSettore() + " ";
		}
		
		if (!GenericUtil.isNullOrEmpty(tCertificatore.getSezione()))
		{
			ordineIscrizione += tCertificatore.getSezione();
		}
		
		soggettoCertificatore.setOrdineIscrizione(ordineIscrizione.trim());
		
		soggettoCertificatore.setDichiarazioneIndipendenza(APEConstants.ID_DICHIARAZIONE_INDIPENDENZA);
		
		soggettoCertificatore.setInformazioniAggiuntive(tAltreInfo.getUltInfo());
		
		ape2015.setSoggettoCertificatore(soggettoCertificatore);
		
		ape2015.setSopralluogoObbligatorio(GenericUtil.convertToBoolean(tAltreInfo.getFlgSopralluogo()));
		
		TypeSoftwareUtilizzato softwareUtilizzato = new TypeSoftwareUtilizzato();
		softwareUtilizzato.setRequisitiRispondenzaRisultati(GenericUtil.convertToBoolean(tAltreInfo.getFlgSw1()));
		softwareUtilizzato.setCalcoloSemplificato(GenericUtil.convertToBoolean(tAltreInfo.getFlgSw2()));
		
		ape2015.setSoftwareUtilizzato(softwareUtilizzato);
		
		ape2015.setDataEmissione(GenericUtil.convertToXMLGregorianCalendar(tCertificato.getDtInizio()));
		
		TypeDatiExtra datiExtra = new TypeDatiExtra();
		
		//System.out.println("Stampo prima dell'eccezione");
		GenericUtil.stampa(tDatiXml2015, false, 3);
		
		datiExtra.setComune(tCertificato.getDescComune());
		datiExtra.setCAP(tCertificato.getCap());
		datiExtra.setProvincia(tCertificato.getDescProv());
		datiExtra.setRegione(APEConstants.DESC_PIEMONTE);
		datiExtra.setGradiGiorno(tDatiGenerali.getGradiGiorno());
		datiExtra.setDataSopralluogo(GenericUtil.convertToXMLGregorianCalendar(tCertificato.getDtInizio()));
		datiExtra.setSoftwareUtilizzato(tDatiXml2015.getSwUtilizzato());
		datiExtra.setNumeroCertificatoSoftware(tDatiXml2015.getNumCertifSw());
		datiExtra.setEphndLim(tDatiEner2015.getEphLimite());
		datiExtra.setEPglnrenRifStandard(tDatiEner2015.getEpglNrenRif());
		datiExtra.setIdCatastale(null); // Noi non lo gestiamo
		datiExtra.setProprietaEdificio(GenericUtil.convertToShort(tDatiGenerali.getFkProprietaEdi()));
		datiExtra.setCodiceEdificioScolastico(tDatiGenerali.getCodiceEdificioScolastico());
		datiExtra.setTipologiaEdilizia(GenericUtil.convertToShort(tDatiGenerali.getFkTipolEdilizia()));
		datiExtra.setTipologiaCostruttiva(GenericUtil.convertToShort(tDatiGenerali.getFkTipolCostruttiva()));
		
		ape2015.setDatiExtra(datiExtra);
		
		
		
		// FUNZIONA, non ho testato il validate
		/*
		
		try {
			JAXBContext mContext = JAXBContext.newInstance("it.csi.sicee.siceegwsiape.jaxb.lib", it.csi.sicee.siceegwsiape.jaxb.lib.Ape2015.class.getClassLoader());
			//mContext = JAXBContext.newInstance(Ape2015.class);
			Marshaller marshaller = mContext.createMarshaller();
			marshaller.setProperty("jaxb.encoding", "UTF-8");
			
			// AD VERIFICARE
//			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); 
//			Schema schema = sf.newSchema(new File("customer.xsd")); 
//			marshaller.setSchema(schema);
			
			
			// output pretty printed
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			//javax.xml.bind.JAXBException: class <ClassName> nor any of its super class is known to this context

			
			StringWriter sw = new StringWriter();
			marshaller.marshal(ape2015, sw);
			marshaller.marshal(ape2015, System.out);

			xml = sw.toString();

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
		//System.out.println("STAMPO L'XML: \n"+xml);
		
		*/
		
		return ape2015;
	}
	
	
	public static SiceeTSiapeLog mapToSiceeTSiapeLog(SiceeTSiape siceeTSiape, Timestamp inizioProcesso, SiapeException sx)
	{


		SiceeTSiapeLog actaLog = new SiceeTSiapeLog();
		actaLog.setDataOperazione(inizioProcesso);
		actaLog.setCodiceEsito(sx.getFaultInfo().getFaultCode());
		actaLog.setMsgEsito(GenericUtil.limitaString(sx.getFaultInfo().getFaultString(), APEConstants.MAX_LUNGHEZZA_200));
		actaLog.setFlgMailInviata(APEConstants.COD_N);
		actaLog.setSiceeTSiape(siceeTSiape);

		return actaLog;
	}
	
	public static SiceeTSiapeLog mapToSiceeTSiapeLog(SiceeTSiape siceeTSiape, Timestamp inizioProcesso, it.enea.siape.ws.soap.WSAPEResponse response)
	{


		SiceeTSiapeLog actaLog = new SiceeTSiapeLog();
		actaLog.setDataOperazione(inizioProcesso);
		actaLog.setCodiceEsito(response.getCodice());
		actaLog.setMsgEsito(GenericUtil.limitaString(response.getMessaggio(), APEConstants.MAX_LUNGHEZZA_200));
		actaLog.setFlgMailInviata(APEConstants.COD_N);
		actaLog.setSiceeTSiape(siceeTSiape);

		return actaLog;
	}
	
	public static SiceeTSiapeLog mapToSiceeTSiapeLog(SiceeTSiape siceeTSiape, Timestamp inizioProcesso, Exception ex)
	{


		SiceeTSiapeLog actaLog = new SiceeTSiapeLog();
		actaLog.setDataOperazione(inizioProcesso);
		actaLog.setCodiceEsito(APEConstants.ERRORE_SISTEMA);
		actaLog.setMsgEsito(GenericUtil.limitaString(""+ex.getMessage(), APEConstants.MAX_LUNGHEZZA_200));
		actaLog.setFlgMailInviata(APEConstants.COD_N);
		actaLog.setSiceeTSiape(siceeTSiape);

		return actaLog;
	}
}
