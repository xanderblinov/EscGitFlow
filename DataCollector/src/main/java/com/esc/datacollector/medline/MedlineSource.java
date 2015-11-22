package com.esc.datacollector.medline;

import java.util.ArrayList;
import java.util.List;

import com.esc.common.util.Pair;

/**
 * Date: 4/18/2015
 * Time: 8:45 PM
 *
 * @author xanderblinov
 */
public class MedlineSource
{
	private List<Pair<String, String>> mFieldList;

	public MedlineSource(final List<Pair<String, String>> fieldList)
	{
		mFieldList = fieldList;
	}

	public MedlineSource()
	{
		mFieldList = new ArrayList<Pair<String, String>>();
	}

	public void addField(String field, String fieldValue)
	{
		mFieldList.add(new Pair<String, String>(field, fieldValue));
	}

	public List<Pair<String, String>> getFieldList()
	{
		return mFieldList;
	}

	@Override
	public String toString()
	{
		return mFieldList.toString();
	}
}


/*

	Article Example


PMID- 23672808
OWN - NLM
STAT- Publisher
DA  - 20130515
IS  - 1096-0805 (Electronic)
IS  - 0022-2011 (Linking)
DP  - 2013 May 11
TI  - Spatial and temporal distribution of pathogenic Wolbachia strain wMelPop in
      Drosophila melanogaster central nervous system under different temperature
      conditions.
LID - S0022-2011(13)00072-4 [pii]
LID - 10.1016/j.jip.2013.05.001 [doi]
AB  - The pathogenic Wolbachia strain, wMelPop, of Drosophila melanogaster is
      propagated in the fly's brain and muscles. To determine how wMelPop spreads in
      the host's central nervous system (CNS) during its life cycle, we used
      whole-mount fluorescent in-situ hybridization to demonstrate the spatial
      distribution of wMelPop in D.melanogaster larvae and adults. To assess the effect
      of temperature on the pattern of wMelPop spread, we performed this analysis under
      moderate (25 degrees C) and high (29 degrees C) temperature conditions. Wolbachia
      distribution pattern in the third instar larva and adult brain was similar at
      both temperatures. wMelPop was generally localized to the subesophageal ganglion
      and the central brain of the host, whereas optic lobe anlagen cells of third
      instar larvae and cells of the optic lobe, lamina and retina of adult flies were
      mostly free of bacteria. Interestingly, high temperature had no significant
      effect on wMelPop titer or localization in the brain during larval development,
      but considerably altered it in adults immediately after eclosion. At both
      temperatures and within all tested stages of the life cycle, the bacterial titer
      varied only slightly between individuals. The observed differences in wMelPop
      titers in the central brain, subesophageal ganglion and optic lobe anlagen cells
      of third instar larva's CNS, together with the observation that these patterns
      are conserved in the adult brain, suggest that Wolbachia distribution is
      determined during fly embryogenesis.
CI  - Copyright (c) 2013. Published by Elsevier Inc.
AD  - Institute of Cytology and Genetics, Siberian Branch of the Russian Academy of
      Sciences, Prospekt Lavrentyeva 10, Novosibirsk 630090, Russia. Electronic
      address: strunov.anton@gmail.com.
FAU - Strunov, Anton
AU  - Strunov A
FAU - Kiseleva, Elena
AU  - Kiseleva E
FAU - Gottlieb, Yuval
AU  - Gottlieb Y
LA  - ENG
PT  - JOURNAL ARTICLE
DEP - 20130511
TA  - J Invertebr Pathol
JT  - Journal of invertebrate pathology
JID - 0014067
EDAT- 2013/05/16 06:00
MHDA- 2013/05/16 06:00
CRDT- 2013/05/16 06:00
PHST- 2012/12/13 [received]
PHST- 2013/04/09 [revised]
PHST- 2013/05/03 [accepted]
AID - S0022-2011(13)00072-4 [pii]
AID - 10.1016/j.jip.2013.05.001 [doi]
PST - aheadofprint
SO  - J Invertebr Pathol. 2013 May 11. pii: S0022-2011(13)00072-4. doi:
      10.1016/j.jip.2013.05.001.
*/
