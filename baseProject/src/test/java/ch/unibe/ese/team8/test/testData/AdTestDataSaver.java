package ch.unibe.ese.team8.test.testData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.unibe.ese.team8.model.Ad;
import ch.unibe.ese.team8.model.AdPicture;
import ch.unibe.ese.team8.model.User;
import ch.unibe.ese.team8.model.dao.AdDao;
import ch.unibe.ese.team8.model.dao.UserDao;

/** This inserts several ad elements into the database. */
@Service
public class AdTestDataSaver {

	@Autowired
	private AdDao adDao;
	@Autowired
	private UserDao userDao;

	@Transactional
	public void saveTestData() throws Exception {
		User bernerBaer = userDao.findByUsername("user@bern.com");
		User ese = userDao.findByUsername("ese@unibe.ch");
		User oprah = userDao.findByUsername("oprah@winfrey.com");
		User jane = userDao.findByUsername("jane@doe.com");
		User hans = userDao.findByUsername("hans@unibe.ch");
		User mathilda = userDao.findByUsername("mathilda@unibe.ch");

		List<User> regRoommatesAdBern = new LinkedList<User>();
		regRoommatesAdBern.add(hans);
		regRoommatesAdBern.add(mathilda);

		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

		Date creationDate1 = formatter.parse("03.10.2014");
		Date creationDate2 = formatter.parse("11.10.2014");
		Date creationDate3 = formatter.parse("25.10.2014");
		Date creationDate4 = formatter.parse("02.11.2014");
		Date creationDate5 = formatter.parse("25.11.2013");
		Date creationDate6 = formatter.parse("01.12.2014");
		Date creationDate7 = formatter.parse("16.11.2014");
		Date creationDate8 = formatter.parse("27.11.2014");
		Date creationDate9 = formatter.parse("01.05.2016");

		Date moveInDate1 = formatter.parse("15.12.2014");
		Date moveInDate2 = formatter.parse("21.12.2014");
		Date moveInDate3 = formatter.parse("01.01.2015");
		Date moveInDate4 = formatter.parse("15.01.2015");
		Date moveInDate5 = formatter.parse("01.02.2015");
		Date moveInDate6 = formatter.parse("01.03.2015");
		Date moveInDate7 = formatter.parse("15.03.2015");
		Date moveInDate8 = formatter.parse("16.02.2015");
		Date moveInDate9 = formatter.parse("01.11.2016");

		Date moveOutDate1 = formatter.parse("31.03.2015");
		Date moveOutDate2 = formatter.parse("30.04.2015");
		Date moveOutDate3 = formatter.parse("31.03.2016");
		Date moveOutDate4 = formatter.parse("01.07.2015");
		Date moveOutDate5 = formatter.parse("30.09.2016");

		String roomDescription1 = "The room is a part of 3.5 rooms apartment completely renovated"
				+ "in 2010 at Kramgasse, Bern. The apartment is about 50 m2 on 1st floor."
				+ "Apt is equipped modern kitchen, hall and balcony. Near to shops and public"
				+ "transportation. Monthly rent is 500 CHF including charges. Internet + TV + landline"
				+ "charges are separate. If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";
		String preferences1 = "Uncomplicated, open minded and easy going person (m / w),"
				+ "non-smoker, can speak English, which of course fits in the WG, and who likes dogs."
				+ "Cleanliness is must. Apart from personal life, sometimes glass of wine,"
				+ "eat and cook together and go out in the evenings.";

		Ad adBern = new Ad();
		List<AdPicture> pictures = new ArrayList<>();
		pictures.add(createPicture(adBern, "/img/test/ad1_1.jpg"));
		pictures.add(createPicture(adBern, "/img/test/ad1_2.jpg"));
		pictures.add(createPicture(adBern, "/img/test/ad1_3.jpg"));

		TestDataUtils.polyfillAd(adBern,
				3011,
				moveInDate1,
				creationDate1,
				moveOutDate1,
				400,
				50,
				"room",
				false,
				true,
				roomDescription1,
				preferences1,
				"One roommate",
				bernerBaer,
				regRoommatesAdBern,
				"Roommate wanted in Bern",
				"Kramgasse 22",
				"Bern",
				true,
				true,
				true,
				true,
				true,
				true,
				true,
				false,
				false,
				pictures);

		adDao.save(adBern);

		String studioDescription2 = "It is small studio close to the"
				+ "university and the bahnhof. The lovely neighbourhood"
				+ "Langgasse makes it an easy place to feel comfortable."
				+ "The studio is close to a Migross, Denner and the Coop."
				+ "The studio is 60m2. It has it own Badroom and kitchen."
				+ "Nothing is shared. The studio is fully furnished. The"
				+ "studio is also provided with a balcony. So if you want to"
				+ "have a privat space this could totally be good place for you."
				+ "Be aware it is only till the end of March. The price is from"
				+ "550- 700 CHF, But there is always room to talk about it.";
		String roomPreferences2 = "I would like to have an easy going person who"
				+ "is trustworthy and can take care of the flat. No animals please."
				+ "Non smoker preferred.";

		Ad adBern2 = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBern2, "/img/test/ad2_1.jpg"));
		pictures.add(createPicture(adBern2, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adBern2, "/img/test/ad2_3.jpg"));

		TestDataUtils.polyfillAd(adBern2,
				3012,
				moveInDate2,
				creationDate2,
				moveOutDate4,
				700,
				60,
				"studio",
				false,
				true,
				studioDescription2,
				roomPreferences2,
				"None",
				ese,
				null, // Roommates list
				"Cheap studio in Bern!",
				"Längassstr. 40",
				"Bern",
				false,
				false,
				false,
				false,
				false,
				false,
				true,
				false,
				false,
				pictures);

		adDao.save(adBern2);

		String studioDescription3 = " In the center of Gundeli (5 Min. away from the"
				+ "station, close to Tellplatz) there is a lovely house, covered with"
				+ "savage wine stocks, without any neighbours but with a garden. The"
				+ "house has two storey, on the first floor your new room is waiting"
				+ "for you. The house is totally equipped with everything a household "
				+ ": washing machine, kitchen, batroom, W-Lan...if you don´t have any"
				+ "furniture, don´t worry, I am sure, we will find something around"
				+ "the house. The price for the room and all included is 480 CHF /month. "
				+ " (29, Graphic designer) and Linda (31, curator) are looking for a"
				+ "new female flatmate from December on.";
		String roomPreferences3 = "smoking female flatmate";

		Ad adBasel = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBasel, "/img/test/ad3_1.jpg"));
		pictures.add(createPicture(adBasel, "/img/test/ad3_2.jpg"));
		pictures.add(createPicture(adBasel, "/img/test/ad3_3.jpg"));

		TestDataUtils.polyfillAd(adBasel,
				4051,
				moveInDate3,
				creationDate3,
				moveOutDate2,
				480,
				10,
				"studio",
				true,
				false,
				studioDescription3,
				roomPreferences3,
				"None",
				bernerBaer,
				null, // Roommates list
				"Nice, bright studio in the center of Basel",
				"Bruderholzstrasse 32",
				"Basel",
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adBasel);

		String studioDescription4 = "Flatshare of 3 persons. Flat with 5 rooms"
				+ "on the second floor. The bedroom is about 60 square meters"
				+ "with access to a nice balcony. In addition to the room, the"
				+ "flat has: a living room, a kitchen, a bathroom, a seperate WC,"
				+ "a storage in the basement, a balcony, a laundry room in the basement."
				+ "The bedroom is big and bright and has a nice parquet floor."
				+ "Possibility to keep some furnitures like the bed.";
		String roomPreferences4 = "an easy going flatmate man or woman between 20 and 30";

		Ad adOlten = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adOlten, "/img/test/ad4_1.png"));
		pictures.add(createPicture(adOlten, "/img/test/ad4_2.png"));
		pictures.add(createPicture(adOlten, "/img/test/ad4_3.png"));

		TestDataUtils.polyfillAd(adOlten,
				4600,
				moveInDate4,
				creationDate4,
				null, // MoveOut Date
				430,
				60,
				"room",
				true,
				false,
				studioDescription4,
				roomPreferences4,
				"One roommate",
				ese,
				null, // Roommates list
				"Roommate wanted in Olten City",
				"Zehnderweg 5",
				"Olten",
				false,
				true,
				true,
				true,
				true,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adOlten);

		String studioDescription5 = "Studio meublé au 3ème étage, comprenant"
				+ "une kitchenette entièrement équipée (frigo, plaques,"
				+ "four et hotte), une pièce à vivre donnant sur un balcon,"
				+ "une salle de bains avec wc. Cave, buanderie et site satellite"
				+ "à disposition.";
		String roomPreferences5 = "tout le monde est bienvenu";

		Ad adNeuchâtel = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_1.jpg"));
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_2.jpg"));
		pictures.add(createPicture(adNeuchâtel, "/img/test/ad5_3.jpg"));

		TestDataUtils.polyfillAd(adNeuchâtel,
				2000,
				moveInDate5,
				creationDate5,
				moveOutDate3,
				410,
				40,
				"studio",
				true,
				false,
				studioDescription5,
				roomPreferences5,
				"None",
				bernerBaer,
				null, // Roommates list
				"Studio extrèmement bon marché à Neuchâtel",
				"Rue de l'Hôpital 11",
				"Neuchâtel",
				true,
				false,
				true,
				true,
				false,
				false,
				true,
				false,
				false,
				pictures);

		adDao.save(adNeuchâtel);

		String houseDescription = "This house is for sale! Good price good value! In a very quiet part of town";
		String housePreference = "Families with kids and animals";

		Ad adHouse = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adHouse, "/img/test/house_1.jpg"));
		pictures.add(createPicture(adHouse, "/img/test/house_2.jpg"));
		TestDataUtils.polyfillAd(adHouse,
				2504,
				moveInDate6,
				creationDate6,
				moveOutDate5,
				400000,
				400,
				"house",
				true,
				true,
				houseDescription,
				housePreference,
				"None",
				ese,
				((List<User>) null), // Roommates list
				"Direkt am See: hübsches Haus",
				"Am Wald 4",
				"Biel/Bienne",
				true,
				true,
				true,
				false,
				false,
				true,
				false,
				true,
				false,
				pictures);

		adDao.save(adHouse);

		String studioDescription6 = "A place just for yourself in a very nice part of Biel."
				+ "A studio for 1-2 persons with a big balcony, bathroom, kitchen and furniture already there."
				+ "It's quiet and nice, very close to the old city of Biel.";
		String roomPreferences6 = "A nice and easy going person. Minimum rent is two months";

		Ad adBiel = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adBiel, "/img/test/ad6_1.png"));
		pictures.add(createPicture(adBiel, "/img/test/ad6_2.png"));
		pictures.add(createPicture(adBiel, "/img/test/ad6_3.png"));

		TestDataUtils.polyfillAd(adBiel,
				2503,
				moveInDate6,
				creationDate6,
				moveOutDate5,
				480,
				10,
				"studio",
				true,
				false,
				studioDescription6,
				roomPreferences6,
				"None",
				ese,
				null, // Roommates list
				"Direkt am Quai: hübsches Studio",
				"Oberer Quai 12",
				"Biel/Bienne",
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adBiel);


		String roomDescription7 = "The room is a part of 3.5 rooms apartment completely renovated"
				+ "in 2010 at Kramgasse, Bern. The apartment is about 50 m2 on 1st floor."
				+ "Apt is equipped modern kitchen, hall and balcony. Near to shops and public"
				+ "transportation. Monthly rent is 500 CHF including charges. Internet + TV + landline"
				+ "charges are separate. If you are interested, feel free to drop me a message"
				+ "to have an appointment for a visit or can write me for any further information";
		String preferences7 = "Uncomplicated, open minded and easy going person (m / w),"
				+ "non-smoker, can speak English, which of course fits in the WG, and who likes dogs."
				+ "Cleanliness is must. Apart from personal life, sometimes glass of wine,"
				+ "eat and cook together and go out in the evenings.";

		Ad adZurich = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adZurich, "/img/test/ad1_3.jpg"));
		pictures.add(createPicture(adZurich, "/img/test/ad1_2.jpg"));
		pictures.add(createPicture(adZurich, "/img/test/ad1_1.jpg"));

		TestDataUtils.polyfillAd(adZurich,
				8000,
				moveInDate7,
				creationDate7,
				moveOutDate5,
				480,
				32,
				"room",
				false,
				false,
				roomDescription7,
				preferences7,
				"One roommate",
				oprah,
				null, // Roommates list
				"Roommate wanted in Zürich",
				"Hauptstrasse 61",
				"Zürich",
				false,
				true,
				false,
				true,
				true,
				true,
				true,
				false,
				false,
				pictures);

		adDao.save(adZurich);


		String studioDescription8 = "It is small studio close to the"
				+ "university and the bahnhof. The lovely neighbourhood"
				+ "Langgasse makes it an easy place to feel comfortable."
				+ "The studio is close to a Migross, Denner and the Coop."
				+ "The studio is 60m2. It has it own Badroom and kitchen."
				+ "Nothing is shared. The studio is fully furnished. The"
				+ "studio is also provided with a balcony. So if you want to"
				+ "have a privat space this could totally be good place for you."
				+ "Be aware it is only till the end of March. The price is from"
				+ "550- 700 CHF, But there is always room to talk about it.";
		String roomPreferences8 = "I would like to have an easy going person who"
				+ "is trustworthy and can take care of the flat. No animals please."
				+ "Non smoker preferred.";

		Ad adLuzern = new Ad();
		pictures = new ArrayList<>();
		pictures.add(createPicture(adLuzern, "/img/test/ad2_3.jpg"));
		pictures.add(createPicture(adLuzern, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adLuzern, "/img/test/ad2_1.jpg"));

		TestDataUtils.polyfillAd(adLuzern,
				6000,
				moveInDate8,
				creationDate2,
				null, // MoveOutDate
				700,
				60,
				"studio",
				false,
				false,
				studioDescription8,
				roomPreferences8,
				"None",
				oprah,
				null, // Roommates list
				"Elegant Studio in Lucerne",
				"Schwanenplatz 61",
				"Luzern",
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adLuzern);

		String studioDescription9 = " In the center of Gundeli (5 Min. away from the"
				+ "station, close to Tellplatz) there is a lovely house, covered with"
				+ "savage wine stocks, without any neighbours but with a garden. The"
				+ "house has two storey, on the first floor your new room is waiting"
				+ "for you. The house is totally equipped with everything a household "
				+ ": washing machine, kitchen, batroom, W-Lan...if you don´t have any"
				+ "furniture, don´t worry, I am sure, we will find something around"
				+ "the house. The price for the room and all included is 480 CHF /month. "
				+ " (29, Graphic designer) and Linda (31, curator) are looking for a"
				+ "new female flatmate from December on.";
		String roomPreferences9 = "smoking female flatmate";

		Ad adAarau = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adAarau, "/img/test/ad3_3.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad3_2.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad3_1.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad2_2.jpg"));
		pictures.add(createPicture(adAarau, "/img/test/ad2_3.jpg"));

		TestDataUtils.polyfillAd(adAarau,
				5000,
				moveInDate3,
				creationDate8,
				moveOutDate4,
				800,
				26,
				"studio",
				true,
				false,
				studioDescription9,
				roomPreferences9,
				"None",
				oprah,
				null, // Roommates list
				"Beautiful studio in Aarau",
				"Bruderholzstrasse 32",
				"Aarau",
				false,
				true,
				false,
				true,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adAarau);

		String studioDescription10 = "Flatshare of 3 persons. Flat with 5 rooms"
				+ "on the second floor. The bedroom is about 60 square meters"
				+ "with access to a nice balcony. In addition to the room, the"
				+ "flat has: a living room, a kitchen, a bathroom, a seperate WC,"
				+ "a storage in the basement, a balcony, a laundry room in the basement."
				+ "The bedroom is big and bright and has a nice parquet floor."
				+ "Possibility to keep some furnitures like the bed.";
		String roomPreferences10 = "an easy going flatmate man or woman between 20 and 30";

		Ad adDavos = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adDavos, "/img/test/ad4_3.png"));
		pictures.add(createPicture(adDavos, "/img/test/ad4_2.png"));
		pictures.add(createPicture(adDavos, "/img/test/ad4_1.png"));

		TestDataUtils.polyfillAd(adDavos,
				7260,
				moveInDate2,
				creationDate4,
				null, // MoveOutDate
				1100,
				74,
				"room",
				true,
				false,
				studioDescription10,
				roomPreferences10,
				"One roommate",
				oprah,
				null, // Roommates list
				"Free room in Davos City",
				"Kathrinerweg 5",
				"Davos",
				false,
				true,
				true,
				true,
				true,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adDavos);

		String studioDescription11 = "Studio meublé au 3ème étage, comprenant"
				+ "une kitchenette entièrement équipée (frigo, plaques,"
				+ "four et hotte), une pièce à vivre donnant sur un balcon,"
				+ "une salle de bains avec wc. Cave, buanderie et site satellite"
				+ "à disposition.";
		String roomPreferences11 = "tout le monde est bienvenu";

		Ad adLausanne = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adLausanne, "/img/test/ad5_3.jpg"));
		pictures.add(createPicture(adLausanne, "/img/test/ad5_2.jpg"));
		pictures.add(createPicture(adLausanne, "/img/test/ad5_1.jpg"));

		TestDataUtils.polyfillAd(adLausanne,
				1000,
				moveInDate5,
				creationDate5,
				moveOutDate3,
				360,
				8,
				"room",
				true,
				false,
				studioDescription11,
				roomPreferences11,
				"None",
				oprah,
				null, // Roommates list
				"tudio extrèmement bon marché à Lausanne",
				"Rue de l'Eglise 26",
				"Lausanne",
				true,
				false,
				true,
				true,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adLausanne);

		String studioDescription12 = "A place just for yourself in a very nice part of Biel."
				+ "A studio for 1-2 persons with a big balcony, bathroom, kitchen and furniture already there."
				+ "It's quiet and nice, very close to the old city of Biel.";
		String roomPreferences12 = "A nice and easy going person. Minimum rent is two months";

		Ad adLocarno = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adLocarno, "/img/test/ad6_3.png"));
		pictures.add(createPicture(adLocarno, "/img/test/ad6_2.png"));
		pictures.add(createPicture(adLocarno, "/img/test/ad6_1.png"));

		TestDataUtils.polyfillAd(adLocarno,
				6600,
				moveInDate6,
				creationDate6,
				moveOutDate5,
				960,
				42,
				"room",
				true,
				false,
				studioDescription12,
				roomPreferences12,
				"None",
				jane,
				null, // Roommates list
				"Malibu-stlye Beachhouse",
				"Kirchweg 12",
				"Locarno",
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				false,
				pictures);

		adDao.save(adLocarno);
		
		String studioDescriptionAuction = "Schönes Studio im Herzen von Schwamendingen! Einkaufsgelegenheiten am Schwamendingerplatz"
				+ ", welcher sich in Laufweite befindet. Tram bis zum HB in nur 15 Minuten";
		String roomPreferencesAuction = "Raucher, Patriot, bevorzugt männlich";

		Ad adAuction = new Ad();

		pictures = new ArrayList<>();
		pictures.add(createPicture(adAuction, "/img/test/ad6_3.png"));
		pictures.add(createPicture(adAuction, "/img/test/ad6_2.png"));
		pictures.add(createPicture(adAuction, "/img/test/ad6_1.png"));

		TestDataUtils.polyfillAd(adAuction,
				8051,
				moveInDate9,
				creationDate9,
				null,
				10000,
				30,
				"studio",
				true,
				false,
				studioDescriptionAuction,
				roomPreferencesAuction,
				"None",
				oprah,
				null, // Roommates list
				"Studio in Schwamendingen zur Auktion",
				"Aprikosenstrass 20",
				"Zürich",
				false,
				true,
				false,
				false,
				false,
				false,
				false,
				true,
				true,
				pictures);
		
		adAuction.setAuctionEndDate(moveInDate6);
		adAuction.setMaxBidder(ese);

		adDao.save(adAuction);

	}

	/**
	 *
	 * @param ad
	 * @param filePath
	 * @return
	 */
	private AdPicture createPicture(final Ad ad, final String filePath) {
		AdPicture picture = new AdPicture();
		picture.setFilePath(filePath);
		return picture;
	}



}