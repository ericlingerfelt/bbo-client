package org.bigbangonline.datastructure.suite;

import org.bigbangonline.datastructure.DataStructure;

/**
 * The Class RegisterDataStructure.
 */
public class RegisterDataStructure extends DataStructure{

	/** The hear_of_suite. */
	private String last_name, first_name, email, institution, address
					, country, research_type, desired_username, desired_password
					, password_hint, notes, hear_of_suite;
	
	/**
	 * Instantiates a new register data structure.
	 */
	public RegisterDataStructure(){initialize();} 
	
	/* (non-Javadoc)
	 * @see org.bigbangonline.datastructure.DataStructure#initialize()
	 */
	public void initialize(){
		setLast_name("");
		setFirst_name("");
		setEmail("");
		setInstitution("");
		setAddress("");
		setCountry("");
		setResearch_type("cosmology");
		setDesired_username("");
		setDesired_password("");
		setPassword_hint("");
		setNotes("");
		setHear_of_suite("");
	}
	
	/**
	 * Gets the last_name.
	 *
	 * @return the last_name
	 */
	public String getLast_name(){return last_name;}
	
	/**
	 * Sets the last_name.
	 *
	 * @param last_name the new last_name
	 */
	public void setLast_name(String last_name){this.last_name = last_name;}
	
	/**
	 * Gets the first_name.
	 *
	 * @return the first_name
	 */
	public String getFirst_name(){return first_name;}
	
	/**
	 * Sets the first_name.
	 *
	 * @param first_name the new first_name
	 */
	public void setFirst_name(String first_name){this.first_name = first_name;}
	
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail(){return email;}
	
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email){this.email = email;}
	
	/**
	 * Gets the institution.
	 *
	 * @return the institution
	 */
	public String getInstitution(){return institution;}
	
	/**
	 * Sets the institution.
	 *
	 * @param institution the new institution
	 */
	public void setInstitution(String institution){this.institution = institution;}
	
	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress(){return address;}
	
	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address){this.address = address;}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry(){return country;}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country){this.country = country;}
	
	/**
	 * Gets the research_type.
	 *
	 * @return the research_type
	 */
	public String getResearch_type(){return research_type;}
	
	/**
	 * Sets the research_type.
	 *
	 * @param research_type the new research_type
	 */
	public void setResearch_type(String research_type){this.research_type = research_type;}
	
	/**
	 * Gets the desired_username.
	 *
	 * @return the desired_username
	 */
	public String getDesired_username(){return desired_username;}
	
	/**
	 * Sets the desired_username.
	 *
	 * @param desired_username the new desired_username
	 */
	public void setDesired_username(String desired_username){this.desired_username = desired_username;}
	
	/**
	 * Gets the desired_password.
	 *
	 * @return the desired_password
	 */
	public String getDesired_password(){return desired_password;}
	
	/**
	 * Sets the desired_password.
	 *
	 * @param desired_password the new desired_password
	 */
	public void setDesired_password(String desired_password){this.desired_password = desired_password;}
	
	/**
	 * Gets the password_hint.
	 *
	 * @return the password_hint
	 */
	public String getPassword_hint(){return password_hint;}
	
	/**
	 * Sets the password_hint.
	 *
	 * @param password_hint the new password_hint
	 */
	public void setPassword_hint(String password_hint){this.password_hint = password_hint;}
	
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes(){return notes;}
	
	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes){this.notes = notes;}
	
	/**
	 * Gets the hear_of_suite.
	 *
	 * @return the hear_of_suite
	 */
	public String getHear_of_suite(){return hear_of_suite;}
	
	/**
	 * Sets the hear_of_suite.
	 *
	 * @param hear_of_suite the new hear_of_suite
	 */
	public void setHear_of_suite(String hear_of_suite){this.hear_of_suite = hear_of_suite;}
	
	/**
	 * Gets the country array.
	 *
	 * @return the country array
	 */
	public String[] getCountryArray(){return countryArray;}
	
	/** The country array. */
	private String[] countryArray = {"United States of America",
										"Afghanistan",
										"Åland Islands",
										"Albania",
										"Algeria",
										"American Samoa",
										"Andorra",
										"Angola",
										"Anguilla",
										"Antigua and Barbuda",
										"Argentina",
										"Armenia",
										"Aruba",
										"Australia",
										"Austria",
										"Azerbaijan",
										"Bahamas",
										"Bahrain",
										"Bangladesh",
										"Barbados",
										"Belarus",
										"Belgium",
										"Belize",
										"Benin",
										"Bermuda",
										"Bhutan",
										"Bolivia",
										"Bosnia and Herzegovina",
										"Botswana",
										"Brazil",
										"British Virgin Islands",
										"Brunei Darussalam",
										"Bulgaria",
										"Burkina Faso",
										"Burundi",
										"Cambodia",
										"Cameroon",
										"Canada",
										"Cape Verde",
										"Cayman Islands",
										"Central African Republic",
										"Chad",
										"Channel Islands",
										"Chile",
										"China",
										"Hong Kong Special Administrative Region of China",
										"Macao Special Administrative Region of China",
										"Colombia",
										"Comoros",
										"Congo",
										"Cook Islands",
										"Costa Rica",
										"Côte d'Ivoire",
										"Croatia",
										"Cuba",
										"Cyprus",
										"Czech Republic",
										"Democratic People's Republic of Korea",
										"Democratic Republic of the Congo",
										"Denmark",
										"Djibouti",
										"Dominica",
										"Dominican Republic",
										"Ecuador",
										"Egypt",
										"El Salvador",
										"Equatorial Guinea",
										"Eritrea",
										"Estonia",
										"Ethiopia",
										"Faeroe Islands",
										"Falkland Islands (Malvinas)",
										"Fiji",
										"Finland",
										"France",
										"French Guiana",
										"French Polynesia",
										"Gabon",
										"Gambia",
										"Georgia",
										"Germany",
										"Ghana",
										"Gibraltar",
										"Greece",
										"Greenland",
										"Grenada",
										"Guadeloupe",
										"Guam",
										"Guatemala",
										"Guernsey",
										"Guinea",
										"Guinea-Bissau",
										"Guyana",
										"Haiti",
										"Holy See",
										"Honduras",
										"Hungary",
										"Iceland",
										"India",
										"Indonesia",
										"Iran, Islamic Republic of",
										"Iraq",
										"Ireland",
										"Isle of Man",
										"Israel",
										"Italy",
										"Jamaica",
										"Japan",
										"Jersey",
										"Jordan",
										"Kazakhstan",
										"Kenya",
										"Kiribati",
										"Kuwait",
										"Kyrgyzstan",
										"Lao People's Democratic Republic",
										"Latvia",
										"Lebanon",
										"Lesotho",
										"Liberia",
										"Libyan Arab Jamahiriya",
										"Liechtenstein",
										"Lithuania",
										"Luxembourg",
										"Madagascar",
										"Malawi",
										"Malaysia",
										"Maldives",
										"Mali",
										"Malta",
										"Marshall Islands",
										"Martinique",
										"Mauritania",
										"Mauritius",
										"Mayotte",
										"Mexico",
										"Micronesia, Federated States of",
										"Monaco",
										"Mongolia",
										"Montenegro",
										"Montserrat",
										"Morocco",
										"Mozambique",
										"Myanmar",
										"Namibia",
										"Nauru",
										"Nepal",
										"Netherlands",
										"Netherlands Antilles",
										"New Caledonia",
										"New Zealand",
										"Nicaragua",
										"Niger",
										"Nigeria",
										"Niue",
										"Norfolk Island",
										"Northern Mariana Islands",
										"Norway",
										"Occupied Palestinian Territory",
										"Oman",
										"Pakistan",
										"Palau",
										"Panama",
										"Papua New Guinea",
										"Paraguay",
										"Peru",
										"Philippines",
										"Pitcairn",
										"Poland",
										"Portugal",
										"Puerto Rico",
										"Qatar",
										"Republic of Korea",
										"Republic of Moldova",
										"Réunion",
										"Romania",
										"Russian Federation",
										"Rwanda",
										"Saint Helena",
										"Saint Kitts and Nevis",
										"Saint Lucia",
										"Saint Pierre and Miquelon",
										"Saint Vincent and the Grenadines",
										"Samoa",
										"San Marino",
										"Sao Tome and Principe",
										"Saudi Arabia",
										"Senegal",
										"Serbia",
										"Seychelles",
										"Sierra Leone",
										"Singapore",
										"Slovakia",
										"Slovenia",
										"Solomon Islands",
										"Somalia",
										"South Africa",
										"Spain",
										"Sri Lanka",
										"Sudan",
										"Suriname",
										"Svalbard and Jan Mayen Islands",
										"Swaziland",
										"Sweden",
										"Switzerland",
										"Syrian Arab Republic",
										"Tajikistan",
										"Thailand",
										"The former Yugoslav Republic of Macedonia",
										"Timor-Leste",
										"Togo",
										"Tokelau",
										"Tonga",
										"Trinidad and Tobago",
										"Tunisia",
										"Turkey",
										"Turkmenistan",
										"Turks and Caicos Islands",
										"Tuvalu",
										"Uganda",
										"Ukraine",
										"United Arab Emirates",
										"United Kingdom of Great Britain and Northern Ireland",
										"United Republic of Tanzania",
										"United States Virgin Islands",
										"Uruguay",
										"Uzbekistan",
										"Vanuatu",
										"Venezuela (Bolivarian Republic of)",
										"Viet Nam",
										"Wallis and Futuna Islands",
										"Western Sahara",
										"Willie Nelson",
										"Yemen",
										"Zambia",
										"Zimbabwe"};
	
}
