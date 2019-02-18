import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;


public class AllOps {
public static int GetWords(LinkedList<ArrayList<String>> lst,String word){
	int cpt=0;
	for(int j=0;j<lst.size();j++){
		ArrayList<String> tmp=new ArrayList<String>();
		tmp=lst.get(j);
		if(tmp.contains(word))cpt++;
		
	}
	return cpt;
	
}

public static ArrayList<String> GetSizeWord(LinkedList<ArrayList<String>> lst,int s){
	int cpt=0;
	ArrayList<String> words=new ArrayList<String>();
	for(int j=0;j<lst.size();j++){
		ArrayList<String> tmp=lst.get(j);
		for(int i=0;i<tmp.size();i++)
			if(tmp.get(i).length()==s)
				words.add(tmp.get(i));
		
	}
	return words;
	
}


public void OptimizeList(LinkedList<ArrayList<String>> lst){
	for(int j=0;j<lst.size();j++){
		ArrayList<String> tmp=lst.get(j);
		for(int i=0;i<tmp.size();i++){
			
			//word
			String word=tmp.get(i).replace("é", "e");
			tmp.set(i, word);
			
			 word=tmp.get(i).replace("ê", "e");
			tmp.set(i, word);
			
			 word=tmp.get(i).replace("è", "e");
				tmp.set(i, word);
			
		lst.set(j, tmp);
		}
		
			
	}
	//System.out.println("////" +lst.get(51));
}

public LinkedList<ArrayList<String>> GetAllReports(LinkedHashMap<String,HashSet<Integer>>AllFreq) throws IOException{
	int cpt_stop_words=0;
	String txt;
	int cpt=0,cpt1=0;
	String TextPerFile="";
	int a=0;		
	
	//LinkedHashMap<String,HashSet<Integer>>AllFreq=new LinkedHashMap<String, HashSet<Integer>>();
	

	LinkedList<String> stopwords=GetStopWords();
    int i=1;
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/compte_rendus_pretraites/compte_rendus_pretraites/1 ("+i+").txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	LinkedList<ArrayList<String>> lst=new LinkedList<ArrayList<String>>(); 
	while(i<=8623){
		 file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/compte_rendus_pretraites/compte_rendus_pretraites/1 ("+i+").txt");
		 in = new BufferedReader(new FileReader(file));
		 
	while ((txt = in.readLine()) != null)
	{
		//System.out.println(txt.replace("é", "e"));
		ArrayList<String> tmp=new ArrayList<String>();
		//System.out.println(txt);
		String[] val=txt.split(" ");
		
		for(int j=0;j<val.length;j++){
			
			if(AllFreq.containsKey(val[j]))
			{
				HashSet<Integer> tmp_set=AllFreq.get(val[j]);
				tmp_set.add(i);
				AllFreq.put(val[j],tmp_set);
			}else{
				HashSet<Integer> tmp_set=new HashSet<Integer>();
				tmp_set.add(i);
				AllFreq.put(val[j],tmp_set);
			}
			
			//val[j].replace("é","e");
			//System.out.println(val[j]);
		if(!stopwords.contains(val[j]))
			tmp.add(val[j]);
		else
			cpt_stop_words++;
		}
		if(tmp.size()!=0)
		lst.add(tmp);
		
	if(tmp.size()==0)
		System.out.println("eRROR "+ i);
		
	//	 System.out.println("kkk  " + i);
		
	}
	
	i++;

}
//	System.out.println(AllFreq);
return lst;
}

public LinkedHashMap<String,ArrayList<String>> GetCIM() throws IOException{
	//LinkedHashMap<ArrayList<String>,String>
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/CR-CODCIM-CODACT.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	LinkedHashMap<String,ArrayList<String>> lst= new LinkedHashMap<String,ArrayList<String>>();
	ArrayList<String> lstCIM=new ArrayList<String>();
	LinkedHashMap<String, Integer> DistinctCIM=new LinkedHashMap<String, Integer>();
	while ((txt = in.readLine()) != null ){
		String[] val=txt.split(" ");
	//	System.out.println(val[0]);
		DistinctCIM.put(val[1],1);
		if (!lst.containsKey(val[0]))
		{
		lstCIM=new ArrayList<String>();
		lstCIM.add(val[1]);
		lst.put(val[0],lstCIM);
		}else{
			lstCIM=lst.get(val[0]);
			if(!lstCIM.contains(val[1])){
			lstCIM.add(val[1]);
			lst.remove(val[0]);
			lst.put(val[0],lstCIM);
			//System.out.println(lstCIM.size() +" "+val[0]);
			}
		}
	}
	//System.out.println(DistinctCIM.size());
	lst.remove(new ArrayList<String>(lst.keySet()).remove(5700));
	lst.remove(new ArrayList<String>(lst.keySet()).remove(6119));
	lst.remove(new ArrayList<String>(lst.keySet()).remove(7075));
	
	return lst;
}


public LinkedHashMap<String, Integer>  GetALLCIM() throws IOException{
	//LinkedHashMap<ArrayList<String>,String>
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/CR-CODCIM-CODACT.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	
	
	LinkedHashMap<String, Integer> DistinctCIM=new LinkedHashMap<String, Integer>();
	while ((txt = in.readLine()) != null ){
		String[] val=txt.split(" ");
	
		DistinctCIM.put(val[1],1);
		
	}
	
	return DistinctCIM;
}


public void WriteToFile(LinkedList<ArrayList<String>>lst,LinkedHashMap<String,HashSet<Integer>>AllFreq) throws IOException{
	int max=GetMaxWordsInReports(lst);
	//LinkedHashMap<String, Integer> AllFreq=NumberDocsContainigTerm1(lst);
	int D=lst.size();
	
	FileWriter writer = null;
	//writer = new FileWriter("f:/RN_input.txt", true);
	//HashSet<Map<String,Integer>>New_lst=ConvertToHashSet(lst);
	for(int i=0;i<lst.size();i++)
		{	
			ArrayList<String> tmp=lst.get(i);
			writer = new FileWriter("f:/RN_input.txt", true);
			if(tmp.size()==0)System.out.println("invalide repport : "+(i+1));
		for(int j=0;j<tmp.size();j++){
			//int ND=NumberDocsContainigTerm(New_lst, tmp.get(j));

			int ND=AllFreq.get(tmp.get(j)).size();
		if(j!=tmp.size()-1)
			{
			
		//	int ND=NumberDocsContainigTerm(lst, tmp.get(j));
			//int ND=AllFreq.get(tmp.get(j));
			//writer.write(String.valueOf(TermFrequency(tmp, tmp.get(j))*Math.log10(D/ND))+' ');
			writer.write(String.valueOf(Math.log10(D/ND))+' ');
			//writer.write(String.valueOf((tmp.get(j)).hashCode())+',');
		//	System.out.println(tmp.size() +" >>  " +i+"   /// " +tmp);
			
			}
		else
		{
			int bigSize=max-tmp.size();
			if(bigSize==0)
				{
			//	int ND=NumberDocsContainigTerm(lst, tmp.get(j));
			//	int ND=AllFreq.get(tmp.get(j));
				//writer.write(String.valueOf(TermFrequency(tmp, tmp.get(j))*Math.log(D/ND)));
				writer.write(String.valueOf(Math.log(D/ND)));
				//writer.write(String.valueOf(TermFrequency(tmp, tmp.get(j))*(Math.log(D/NumberDocsContainigTerm(lst, tmp.get(j))))));
				}
			else{
				
			//	int ND=NumberDocsContainigTerm(lst, tmp.get(j));
			//	int ND=AllFreq.get(tmp.get(j));
				//writer.write(String.valueOf(TermFrequency(tmp, tmp.get(j))*Math.log(D/ND))+' ');
				writer.write(String.valueOf(Math.log(D/ND))+' ');
				//writer.write(String.valueOf(TermFrequency(tmp, tmp.get(j))*(Math.log(lst.size()/NumberDocsContainigTerm(lst, tmp.get(j)))))+',');
			//writer.write(String.valueOf((tmp.get(j).hashCode())+','));
			
			for(int p=0;p<bigSize;p++){
				if(p!=bigSize-1)
				writer.write("0.0 ");
				else
					writer.write("0.0");
			}
		
		}}}
		writer.write('\n');
		writer.close();
	//	System.out.println(i);
		}
	
	
}
public void DeleteWordBySize(LinkedList<ArrayList<String>>lst,int s){
	
	while(GetSizeWord(lst, s).size()!=0)
	for(int j=0;j<lst.size();j++){
		ArrayList<String> tmp=lst.get(j);
		for(int i=0;i<tmp.size();i++)
			if(tmp.get(i).length()==s)
				tmp.remove(i);
		lst.set(j, tmp);
	}
}
public int GetMaxWordsInReports(LinkedList<ArrayList<String>>lst){
	int max=lst.get(0).size();
	for(int j=1;j<lst.size();j++)
	if(lst.get(j).size()>max)max=lst.get(j).size();	
return max;			
}
public int GetMaxCIMwords(LinkedHashMap<String,ArrayList<String>> lst){
	int max=0;
	for (String val:lst.keySet())
	if(lst.get(val).size()>max)max=lst.get(val).size();
	return max;
}

public void WriteOUTPUTtoFile(LinkedHashMap<String,ArrayList<String>>lst) throws IOException{
	int max=GetMaxCIMwords(lst);
	LinkedHashMap<String, Integer> AllCIM=GetALLCIM();
	int pos=0;
	FileWriter writer = null;
	//writer = new FileWriter("f:/RN_input.txt", true);
	for (ArrayList<String> val:lst.values()){
		writer = new FileWriter("f:/RN_output.txt", true);
		for(int i=0;i<val.size();i++)
			if(i!=val.size()-1)
				//writer.write(String.valueOf(val.get(i).hashCode())+' ');
			{
				pos = new ArrayList<String>(AllCIM.keySet()).indexOf(val.get(i));
				writer.write(String.valueOf(pos+1)+' ');
			}
				
			else
			{	if(max==val.size())
			{
				//writer.write(String.valueOf(val.get(i).hashCode()));
				pos = new ArrayList<String>(AllCIM.keySet()).indexOf(val.get(i));
				writer.write(String.valueOf(pos+1));
			}
			else
				{
				//writer.write(String.valueOf(val.get(i).hashCode())+' ');
				pos = new ArrayList<String>(AllCIM.keySet()).indexOf(val.get(i));
				writer.write(String.valueOf(pos+1)+' ');
				}
			
			//	System.out.println(String.valueOf(val.get(i).hashCode()));
			}
		for(int p=0;p<max-val.size();p++){
			if(p!=max-val.size()-1)
			writer.write("NaN ");
			else
				writer.write("NaN");
		}
		writer.write('\n');
		writer.close();
	}
			
		}

public LinkedList<ArrayList<Integer>> WriteOUTPUT_Binary_toFile(LinkedHashMap<String,ArrayList<String>>lst) throws IOException{
	int max=GetMaxCIMwords(lst);
	LinkedHashMap<String, Integer> AllCIM=GetALLCIM();
	LinkedList<ArrayList<Integer>> binList=new LinkedList<ArrayList<Integer>>();
	int pos=0;
	FileWriter writer = null;
	writer = new FileWriter("f:/RN_output000.txt", true);
	for (ArrayList<String> val:lst.values()){
		
			ArrayList<Integer>tmp=new ArrayList<Integer>();
			for(int j=0;j<AllCIM.size();j++)
				tmp.add(0);
		for(int i=0;i<val.size();i++)	
			{
				pos = new ArrayList<String>(AllCIM.keySet()).indexOf(val.get(i));
				tmp.set(pos, 1);	
			}
				for(int l=0;l<tmp.size();l++)
					if(l!=tmp.size()-1)
						writer.write(String.valueOf(tmp.get(l))+',');
					else
						writer.write(String.valueOf(tmp.get(l)));
				writer.write('\n');
			binList.add(tmp);
			}
	return binList;
		
	}
			
public LinkedList<ArrayList<Integer>> WriteOUTPUT_Binary_toFile_NEW_BINARY(LinkedHashMap<String,ArrayList<String>>lst) throws IOException{
	int max=GetMaxCIMwords(lst);
	LinkedHashMap<String, Integer> AllCIM=GetALLCIM();
	LinkedList<ArrayList<Integer>> binList=new LinkedList<ArrayList<Integer>>();
	int pos=0;
	FileWriter writer = null;
	writer = new FileWriter("f:/RN_outputZRAX.txt", true);
	for (ArrayList<String> val:lst.values()){
		writer = new FileWriter("f:/RN_outputZRAX.txt", true);
		System.out.println(val.get(0));
	writer.write(GetBIN(val.get(0)));
	writer.write('\n');
			writer.close();
			//binList.add(tmp);
	
			}
	return binList;
		
	}
		
	
	public int TermFrequency(ArrayList<String>lst,String word){
		int tf=0;
		for(int i=0;i<lst.size();i++)
			if(lst.get(i).equals(word))tf++;
		return tf;
	}
	public int NumberDocsContainigTerm(HashSet<Map<String,Integer>> lst,String word){
		int nbr=0;
		int i=1;
		  for (Iterator<Map<String,Integer>> it = lst.iterator(); it.hasNext(); ) {
		        Map<String,Integer> f = it.next();
		        
		        if(f.containsKey(word))nbr++;
		  
		        
		  i++;
		  }
		
		  System.out.println("ok");
			return nbr;
		
	}
	
	public LinkedHashSet<Map<String,Integer>> ConvertToHashSet(LinkedList<ArrayList<String>>lst){
		LinkedHashSet<Map<String,Integer>> NewList=new LinkedHashSet<Map<String,Integer>>();
		for(int i=0;i<lst.size();i++){
			Map<String, Integer> tmp1 = new HashMap<String, Integer>();
			Map<String, Integer> map2 = (Map<String, Integer>)tmp1;
			
			ArrayList<String> tmp=lst.get(i);
			for(int k=0;k<tmp.size();k++)
			{
				Integer count = map2.get(tmp.get(k));
				if(count==null)
					map2.put(tmp.get(k),1);
				else
					map2.put(tmp.get(k), ++count);
			}
			map2.put("endOfFile", i);
			System.out.println(i + "  " +NewList.add(map2));
		//	System.out.println(i +" "+map2);
		}
		return NewList;
	}
	public LinkedHashMap<String, Integer> NumberDocsContainigTerm1(LinkedList<ArrayList<String>>lst){
		int nbr=0;
		//LinkedList<ArrayList<Integer>>lstFrequence=new LinkedList<ArrayList<Integer>>();
		LinkedHashMap<String, Integer>Freq=new LinkedHashMap<String, Integer>();
		for(int i=0;i<lst.size();i++)
			{
		//	ArrayList<Integer>tmpFreq=new ArrayList<Integer>();
			ArrayList<String>tmp=lst.get(i);
			for(int j=0;j<tmp.size();j++)
			{
				if(Freq.containsKey(tmp.get(j)))
					{
					nbr=Freq.get(tmp.get(j))+1;
					//Freq.remove(tmp.get(j));
					
					Freq.put(tmp.get(j), nbr);
					break;
					}
				else
				{
					Freq.put(tmp.get(j), 1);
					break;
				}
					
				
				}
			
			//lstFrequence.add(tmpFreq);
		//System.out.println(i);
			}
		
			return Freq;
		
	}
	
	
	public LinkedHashMap<String,ArrayList<String>> GetNGAP() throws IOException{
		//LinkedHashMap<ArrayList<String>,String>
		File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/CR-CODCIM-CODACT.txt");
		BufferedReader in = new BufferedReader(new FileReader(file));
		String txt;
		LinkedHashMap<String,ArrayList<String>> lst= new LinkedHashMap<String,ArrayList<String>>();
		ArrayList<String> lstCIM=new ArrayList<String>();
		LinkedHashMap<String, Integer> DistinctNGAP=new LinkedHashMap<String, Integer>();
		while ((txt = in.readLine()) != null ){
			String[] val=txt.split(" ");
		
			DistinctNGAP.put(val[2],1);
			if (!lst.containsKey(val[0]))
			{
			lstCIM=new ArrayList<String>();
			lstCIM.add(val[2]);
			lst.put(val[0],lstCIM);
			}else{
				lstCIM=lst.get(val[0]);
				if(!lstCIM.contains(val[2])){
				lstCIM.add(val[2]);
				lst.remove(val[0]);
				lst.put(val[0],lstCIM);
				//System.out.println(lstCIM.size() +" "+val[0]);
				}
			}
		}
		//System.out.println(DistinctCIM.size());
		lst.remove(new ArrayList<String>(lst.keySet()).remove(5700));
		lst.remove(new ArrayList<String>(lst.keySet()).remove(6119));
		lst.remove(new ArrayList<String>(lst.keySet()).remove(7075));
		return lst;
	}
	
	public LinkedList<ArrayList<Integer>> WriteOUTPUT_NGAP_Binary_toFile(LinkedHashMap<String,ArrayList<String>>lst) throws IOException{
		int max=GetMaxCIMwords(lst);
		int ii=0;
		LinkedHashMap<String, Integer> AllCIM=GetAllNGAP();
		LinkedList<ArrayList<Integer>> binList=new LinkedList<ArrayList<Integer>>();
		int pos=0;
		FileWriter writer = null;
		writer = new FileWriter("f:/RN_output222.txt", true);
		for (ArrayList<String> val:lst.values()){
			writer = new FileWriter("f:/RN_output222.txt", true);
			System.out.println(ii);
				ArrayList<Integer>tmp=new ArrayList<Integer>();
				for(int j=0;j<AllCIM.size();j++)
					tmp.add(0);
			for(int i=0;i<val.size();i++)	
				{
					pos = new ArrayList<String>(AllCIM.keySet()).indexOf(val.get(i));
					tmp.set(pos, 1);	
				}
					for(int l=0;l<tmp.size();l++)
						if(l!=tmp.size()-1)
							writer.write(String.valueOf(tmp.get(l))+',');
						else
							writer.write(String.valueOf(tmp.get(l)));
					writer.write('\n');
				binList.add(tmp);
				ii++;
				writer.close();
				}
		return binList;
			
		}
	
	public LinkedHashMap<String, Integer>  GetAllNGAP() throws IOException{
		//LinkedHashMap<ArrayList<String>,String>
		File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/CR-CODCIM-CODACT.txt");
		BufferedReader in = new BufferedReader(new FileReader(file));
		String txt;
		
		
		LinkedHashMap<String, Integer> DistinctCIM=new LinkedHashMap<String, Integer>();
		while ((txt = in.readLine()) != null ){
			String[] val=txt.split(" ");
		
			DistinctCIM.put(val[2],1);
			
		}
		
		return DistinctCIM;
	}
	
	public LinkedList<String> GetStopWords() throws IOException{
		File file = new File("F:/stopwordslist.txt");
		BufferedReader in = new BufferedReader(new FileReader(file));
		LinkedList<String> lst=new LinkedList<String>(); 
			 String txt;
		while ((txt = in.readLine()) != null)
			lst.add(txt);
		return lst;
	}
	
	public void WriteToOneCollection(HashSet<Map<String,Integer>>lst) throws IOException{
		FileWriter writer = null;
		int k=0;
		
		Iterator<Map<String,Integer>> iterator = lst.iterator();
		while (iterator.hasNext()) {
			{	Map<String, Integer> map=iterator.next();
			System.out.println(map);
			for (Map.Entry<String, Integer> entry : map.entrySet())
			{ 
	     
		    	   writer = new FileWriter("f:/ALL_Reports.txt", true);
			
				writer.write(entry.getKey() +' ');
		       }
			
			}
	        System.out.println(k);
	        k++;
	        writer.write('\n');
			writer.close();
	}
		
		
		
			
		}

	
	public String GetBIN(String val){
		String[] tmp=val.split("\\.");
		String first=tmp[0];
		String other=Value(first.charAt(0));
		String other2=first.charAt(1)+""+first.charAt(2);
		other2=Integer.toString(Integer.parseInt(other2),2);
		while(other2.length()<7)other2="0"+other2; 
		int i=1;
		String total=other+other2;
		while(i<tmp.length){ 
			String b=tmp[i];
			b=Integer.toString(Integer.parseInt(b),2);
			while(b.length()<7)b="0"+b;
			total=total+b;
			i++;
		}
		if(total.length()==19)total=total+"0000000";
			if(total.length()==12) total=total+"00000000000000";
		//if(tmp.length==1)total=total+"00000000000000";
			//if(tmp.length==2)total=total+total+"0000000";
				
					
		
	return total;}
	
	public String Value(char a)
	{
		switch(a)
		{case 'A': return "00001";case 'B': return "00010";case 'C': return "00011";case 'D': return "00100";case 'E': return "00101";
		case 'F': return "00110";case 'G': return "00111";case 'H': return "01000";case 'I': return "01001";case 'J': return "01010";
		case 'K': return "01011";case 'L': return "01100";case 'M': return "01101";case 'N': return "01110";case 'O': return "01111";
		case 'P': return "10000";case 'Q': return "10001";case 'R': return "10010";case 'S': return "10011";case 'T': return "10100";
		case 'U': return "10101";case 'V': return "10110";case 'W': return "10111";case 'X': return "11000";case 'Y': return "11001";
		case 'Z': return "11010";
		
		
		}
	return null;}

	
	//NEW Method
public LinkedHashMap<String, ArrayList<String>> getConceptTerms() throws IOException{
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/NEW Rn NGAP.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	
	LinkedList<String> stopWords=GetStopWords();
	LinkedHashMap<String, ArrayList<String>> ConceptTerms=new LinkedHashMap<String, ArrayList<String>>();
	while ((txt = in.readLine()) != null ){
		ArrayList<String> lst=new ArrayList<String>();
		String[] val=txt.split(" ");
		for(int i=1;i<val.length;i++)
		{
			if(!stopWords.contains(val[i]))
			lst.add(val[i]);
		}
		ConceptTerms.put(val[0], lst);
	}
	return ConceptTerms;
}

public LinkedHashMap<String, ArrayList<String>> getTermConcepts() throws IOException{
	
	LinkedHashMap<String, ArrayList<String>> ConceptTerms=getConceptTerms();
	
	
	
	LinkedHashMap<String, ArrayList<String>> TermConcepts=new LinkedHashMap<String, ArrayList<String>>();
	for (Map.Entry<String, ArrayList<String>> entry : ConceptTerms.entrySet()){
		ArrayList<String> tmp=entry.getValue();
		
		for(int i=0;i<tmp.size();i++){
			ArrayList<String> tmp_concept=new ArrayList<String>();
			if(!TermConcepts.containsKey(tmp.get(i))){
				tmp_concept.add(entry.getKey());
				TermConcepts.put(tmp.get(i), tmp_concept);
				}else{
					tmp_concept=TermConcepts.get(tmp.get(i));
					tmp_concept.add(entry.getKey());
					TermConcepts.put(tmp.get(i), tmp_concept);
				}
				
		}
	}
	
	FileWriter writer = null;
	writer = new FileWriter("f:/RN_TermConcept_NGAP.txt", true);
	for (Map.Entry<String, ArrayList<String>> entry : TermConcepts.entrySet()){
		writer = new FileWriter("f:/RN_TermConcept_NGAP.txt", true);
		ArrayList<String> tmp=entry.getValue();
		writer.write(entry.getKey()+" ");
		for(int i=0;i<tmp.size();i++){
			writer.write(tmp.get(i)+" ");
		}
		writer.write('\n');
		writer.close();
	}
	return TermConcepts;
}

public HashSet<String> TrainNetwork() throws IOException{
	LinkedHashMap<String, ArrayList<String>> TermConcepts=getTermConcepts();
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/compte_rendus_pretraites/compte_rendus_pretraites/test.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	ArrayList<String> Report=new ArrayList<String>();
	while ((txt = in.readLine()) != null ){
		String[] val=txt.split(" ");
		for(int i=0;i<val.length;i++)
			Report.add(val[i]);
	}
	HashSet<String> C=new HashSet<String>();
	LinkedList<ArrayList<String>> all_c=new LinkedList<ArrayList<String>>();
	for(int i=0;i<Report.size();i++){
		
	if(TermConcepts.get(Report.get(i))!=null){
		ArrayList<String>tmp=TermConcepts.get(Report.get(i));
		all_c.add(tmp);
	for(int j=0;j<tmp.size();j++)
		C.add(tmp.get(j));
	}
	
}
	
	
	
	
	
	HashSet<String> lst_Inter=new HashSet<String>();
	for(int k=0;k<all_c.size();k++){
	ArrayList<String>tmp=all_c.get(k);
	for(int j=0;j<tmp.size();j++)
	{
		boolean exist=true;
		for(int m=0;m<all_c.size();m++){
		if(!all_c.get(m).contains(tmp.get(j)))
			{
			exist=false;
			break;
			}
		}
		if(exist)lst_Inter.add(tmp.get(j));
	}
}
	
	
	
	
	
	
	
	
return lst_Inter;	
}
//Seconde Method Term Concept from Reports itselfs

public LinkedHashMap<String,ArrayList<String>> getConceptTerm_Reports() throws IOException{
	LinkedHashMap<String,HashSet<Integer>>AllFreq=new LinkedHashMap<String, HashSet<Integer>>();
	LinkedList<ArrayList<String>>reports=	GetAllReports(AllFreq);
	//LinkedHashMap<String,ArrayList<String>>cim=GetCIM();
	LinkedHashMap<String,ArrayList<String>>cim=GetNGAP();
	OptimizeList(reports);
	LinkedHashMap<String,ArrayList<String>> ConceptTerms=new LinkedHashMap<String, ArrayList<String>>();
	int i=0;
	for (Map.Entry<String, ArrayList<String>> entry : cim.entrySet()){
		ArrayList<String> tmp=entry.getValue();
		
		for(int j=0;j<reports.get(i).size();j++)
		{
			ArrayList<String> concepts=new ArrayList<String>();
			for(int k=0;k<tmp.size();k++)
				concepts.add(tmp.get(k));
			
			if(!ConceptTerms.containsKey(reports.get(i).get(j))){
				ConceptTerms.put(reports.get(i).get(j), concepts);
			}else{
					ArrayList<String> concepts_already=ConceptTerms.get(reports.get(i).get(j));
				for(int p=0;p<tmp.size();p++)
					concepts_already.add(tmp.get(p));
				ConceptTerms.put(reports.get(i).get(j), concepts_already);
			}
							
			}
			i++;
		}
		
	FileWriter writer = null;
	writer = new FileWriter("f:/RN_TermConcept_2Method_NGAP.txt", true);
	for (Map.Entry<String, ArrayList<String>> entry : ConceptTerms.entrySet()){
		writer = new FileWriter("f:/RN_TermConcept_2Method_NGAP.txt", true);
		ArrayList<String> tmp=entry.getValue();
		writer.write(entry.getKey()+" ");
		for(int m=0;m<tmp.size();m++){
			writer.write(tmp.get(m)+" ");
		}
		writer.write('\n');
		writer.close();
	}
		return ConceptTerms;
			
	}

public HashSet<String> TrainNetwork_Reports() throws IOException{
	LinkedHashMap<String, ArrayList<String>> TermConcepts=Train_FULL_Concepts();
	File file = new File("D:/Master SII USTHB/S2/projetrseauxdeneurones/compte_rendus_pretraites/compte_rendus_pretraites/test.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	ArrayList<String> Report=new ArrayList<String>();
	while ((txt = in.readLine()) != null ){
		String[] val=txt.split(" ");
		for(int i=0;i<val.length;i++)
			Report.add(val[i]);
	}
	ArrayList<String> lst_C_Array=new ArrayList<String>();
	HashSet<String> C=new HashSet<String>();
	LinkedList<ArrayList<String>> all_c=new LinkedList<ArrayList<String>>();
	ArrayList<String> all_con_rep=new ArrayList<String>();
	for(int i=0;i<Report.size();i++){
		
	if(TermConcepts.get(Report.get(i))!=null){
		ArrayList<String>tmp=TermConcepts.get(Report.get(i));
		all_c.add(tmp);
	for(int j=0;j<tmp.size();j++)
		{
		if (C.add(tmp.get(j)))
			{
			all_con_rep.add(tmp.get(j));
			lst_C_Array.add(tmp.get(j));
			}
			
		}
	
	}
	}
	//Intersection
	//HashSet<String> lst_Inter=new HashSet<String>();
	//Une liste qui d�termine le concept le plus fr�quent
	LinkedHashMap<String,Integer> lst_C=new LinkedHashMap<String, Integer>();
	
	for(int k=0;k<all_con_rep.size();k++){
		for(int m=0;m<all_c.size();m++){
			if(all_c.get(m).contains(all_con_rep.get(k))){
				if(!lst_C.containsKey(all_con_rep.get(k)))
				{
					lst_C.put(all_con_rep.get(k), 1);
				}else{
				int val=lst_C.get(all_con_rep.get(k));
				val++;
				lst_C.put(all_con_rep.get(k),val);
				}
						
			}
		}
	}
	/*
	map.entrySet().stream()
    .sorted(Map.Entry.comparingByValue())
    .forEach(entry -> String );
	*/
	
	
		
		int max_conc=0;
		String c="none";
		ArrayList<String> Final_Con=new ArrayList<String>();
		for(int j=0;j<lst_C_Array.size();j++){
			if(lst_C.get(lst_C_Array.get(j))>max_conc)
			{
				c=lst_C_Array.get(j);
				max_conc=lst_C.get(lst_C_Array.get(j));
			}
		}
		Final_Con.add(c);
		
		
		lst_C.remove(c);
		
		lst_C_Array.remove(c);
		max_conc=0;
		c="";
		for(int j=0;j<lst_C_Array.size();j++){
			if(lst_C.get(lst_C_Array.get(j))>max_conc)
			{
				c=lst_C_Array.get(j);
				max_conc=lst_C.get(lst_C_Array.get(j));
			}
		}
		Final_Con.add(c);
		
		
		lst_C.remove(c);
		
		lst_C_Array.remove(c);
		max_conc=0;
		c="";
		for(int j=0;j<lst_C_Array.size();j++){
			if(lst_C.get(lst_C_Array.get(j))>max_conc)
			{
				c=lst_C_Array.get(j);
				max_conc=lst_C.get(lst_C_Array.get(j));
			}
		}
		Final_Con.add(c);
		
		
		lst_C.remove(c);
		
		lst_C_Array.remove(c);
		
		max_conc=0;
		c="";
		for(int j=0;j<lst_C_Array.size();j++){
			if(lst_C.get(lst_C_Array.get(j))>max_conc)
			{
				c=lst_C_Array.get(j);
				max_conc=lst_C.get(lst_C_Array.get(j));
			}
		}
		Final_Con.add(c);
		
		
		lst_C.remove(c);
		
		lst_C_Array.remove(c);
		
		max_conc=0;
		c="";
		for(int j=0;j<lst_C_Array.size();j++){
			if(lst_C.get(lst_C_Array.get(j))>max_conc)
			{
				c=lst_C_Array.get(j);
				max_conc=lst_C.get(lst_C_Array.get(j));
			}
		}
		Final_Con.add(c);
		
		
		lst_C.remove(c);
		
		lst_C_Array.remove(c);
				System.out.println(Final_Con);
		//System.out.println(lst_C);
	//return lst_C;	
	return null;
		}


public LinkedHashMap<String, ArrayList<String>> Train_FULL_Concepts() throws IOException{
	LinkedHashMap<String, ArrayList<String>> ALL=new LinkedHashMap<String, ArrayList<String>>();


	//File file = new File("f:/ALL_TERM_CONCEPT.txt");
	File file = new File("f:/RN_TermConcept_2Method_NGAP.txt");
	BufferedReader in = new BufferedReader(new FileReader(file));
	String txt;
	while ((txt = in.readLine()) != null ){
		String[] val=txt.split(" ");
		 ArrayList<String> tmp=new ArrayList<String>();
		 for(int i=1;i<val.length;i++){
			 tmp.add(val[i]);
		 }
		 if(!ALL.containsKey(val[0]))
			 ALL.put(val[0], tmp);
		 else{
			 ArrayList<String> tmp2=ALL.get(val[0]);
			 for(int j=0;j<tmp2.size();j++)
				 if(!tmp.contains(tmp2.get(j)))
					 tmp.add(tmp2.get(j));
			 ALL.put(val[0],tmp);
		 }
	}
	
	
	
	return ALL;
	
	
}


}


