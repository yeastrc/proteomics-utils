package org.yeastrc.proteomics.peptide.isotope_label;

import java.util.HashMap;
import java.util.Map;

public class LabelFactory {

	// singleton
	private LabelFactory() { }
	private static final LabelFactory _INSTANCE = new LabelFactory();
	public static LabelFactory getInstance() { return _INSTANCE; }

	private static final Map<String, IsotopeLabel> _LABEL_MAP;
	
	static {
		
		_LABEL_MAP = new HashMap<>();
		
		_LABEL_MAP.put( "13C", new Label13C() );
		_LABEL_MAP.put( "15N", new Label15N() );
		_LABEL_MAP.put( "18O", new Label18O() );
		_LABEL_MAP.put( "2H", new Label2H() );
		
	}
	
	/**
	 * Get the desired isotope label object.
	 * 
	 * @param labelName The name of the label, must be "13C", "15N", "18O", or "2H"
	 * @return
	 * @throws NoSuchLabelException
	 */
	public IsotopeLabel getLabel( String labelName ) throws NoSuchLabelException {
		
		if( !_LABEL_MAP.containsKey( labelName ) )
			throw new NoSuchLabelException( "No such label defined: " + labelName );
		
		return _LABEL_MAP.get( labelName );
		
	}
	
	
}
