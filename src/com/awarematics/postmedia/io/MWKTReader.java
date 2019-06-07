package com.awarematics.postmedia.io;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.Assert;

import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.types.mediamodel.FoV;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MInstant;
import com.awarematics.postmedia.types.mediamodel.MInt;
import com.awarematics.postmedia.types.mediamodel.MLineString;
import com.awarematics.postmedia.types.mediamodel.MMultiPoint;
import com.awarematics.postmedia.types.mediamodel.MPhoto;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;
import com.awarematics.postmedia.types.mediamodel.MString;
import com.awarematics.postmedia.types.mediamodel.MVideo;

public class MWKTReader {
	private static final String EMPTY = "EMPTY";
	private static final String COMMA = ",";
	private static final String L_PAREN = "(";
	private static final String R_PAREN = ")";
	private MGeometryFactory geometryFactory;
	private GeometryFactory geometryFactory2;
	private StreamTokenizer tokenizer;
	private String TempText;
	// Not yet used (useful if we want to read Z, M and ZM WKT)
	@SuppressWarnings("unused")
	private boolean z;
	@SuppressWarnings("unused")
	private boolean m;

	public MWKTReader() {
		this(new MGeometryFactory());
	}

	public MWKTReader(MGeometryFactory geometryFactory) {
		this.geometryFactory = geometryFactory;
		geometryFactory.getPrecisionModel();
	}

	public Object read(String wellKnownText) throws ParseException, org.locationtech.jts.io.ParseException {
		TempText = wellKnownText;
		StringReader reader = new StringReader(TempText);
		try {
			return read(reader);
		} finally {
			reader.close();
		}
	}

	public Object read(Reader reader) throws ParseException, org.locationtech.jts.io.ParseException {
		tokenizer = new StreamTokenizer(reader);
		// set tokenizer to NOT parse numbers
		tokenizer.resetSyntax();
		tokenizer.wordChars('a', 'z');
		tokenizer.wordChars('A', 'Z');
		tokenizer.wordChars(128 + 32, 255);
		tokenizer.wordChars('0', '9');
		tokenizer.wordChars('-', '-');
		tokenizer.wordChars('+', '+');
		tokenizer.wordChars('.', '.');
		tokenizer.whitespaceChars(0, ' ');
		tokenizer.commentChar('#');
		z = false;
		m = false;

		try {
			return readGeometryTaggedText();
		} catch (IOException e) {
			throw new ParseException(e.toString(), 0);
		}
	}

	private String getNextCloser() throws IOException, ParseException {
		String nextWord = getNextWord();
		// if (nextWord.equals(R_PAREN)) {
		// return nextWord;
		// }
		// parseErrorExpected(R_PAREN);
		return nextWord;
	}

	private String getNextWord() throws IOException, ParseException {
		int type = tokenizer.nextToken();
		switch (type) {
		case StreamTokenizer.TT_WORD:
			String word = tokenizer.sval;
			if (word.equalsIgnoreCase(EMPTY))
				return EMPTY;
			return word;

		case '(':
			return L_PAREN;
		case ')':
			return R_PAREN;
		case ',':
			return COMMA;
		}
		parseErrorExpected("word");
		return null;
	}

	private void parseErrorExpected(String expected) throws ParseException {
		// throws Asserts for tokens that should never be seen
		if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
			Assert.shouldNeverReachHere("Unexpected NUMBER token");
		if (tokenizer.ttype == StreamTokenizer.TT_EOL)
			Assert.shouldNeverReachHere("Unexpected EOL token");

		String tokenStr = tokenString();
		parseErrorWithLine("Expected " + expected + " but found " + tokenStr);
	}

	private void parseErrorWithLine(String msg) throws ParseException {
		throw new ParseException(msg + " (line " + tokenizer.lineno() + ")", 0);
	}

	/**
	 * Gets a description of the current token
	 *
	 * @return a description of the current token
	 */
	private String tokenString() {
		switch (tokenizer.ttype) {
		case StreamTokenizer.TT_NUMBER:
			return "<NUMBER>";
		case StreamTokenizer.TT_EOL:
			return "End-of-Line";
		case StreamTokenizer.TT_EOF:
			return "End-of-Stream";
		case StreamTokenizer.TT_WORD:
			return "'" + tokenizer.sval + "'";
		}
		return "'" + (char) tokenizer.ttype + "'";
	}

	private String getNextEmptyOrOpener() throws IOException, ParseException {
		String nextWord = getNextWord();
		if (nextWord.equals(EMPTY) || nextWord.equals(L_PAREN)) {
			return nextWord;
		}
		parseError(EMPTY + " or " + L_PAREN);
		return null;
	}

	private void parseError(String expected) throws ParseException {
		// throws Asserts for tokens that should never be seen
		if (tokenizer.ttype == StreamTokenizer.TT_NUMBER)
			Assert.shouldNeverReachHere("Unexpected NUMBER token");
		if (tokenizer.ttype == StreamTokenizer.TT_EOL)
			Assert.shouldNeverReachHere("Unexpected EOL token");

		String tokenStr = tokenString();
		throw new ParseException("Expected " + expected + " but found " + tokenStr, 0);
	}

	private Coordinate getPreciseCoordinate() throws IOException, ParseException {
		Coordinate coord = new Coordinate();
		coord.x = getNextNumber();
		coord.y = getNextNumber();
		if (isNumberNext()) {
			coord.z = getNextNumber();
		}
		return coord;
	}

	private double getNextNumber() throws IOException, ParseException {
		int type = tokenizer.nextToken();
		switch (type) {
		case StreamTokenizer.TT_WORD: {
			try {
				return Double.parseDouble(tokenizer.sval);
			} catch (NumberFormatException ex) {
				throw new ParseException("Invalid number: " + tokenizer.sval, 0);
			}
		}
		}
		parseError("number");
		return 0.0;
	}

	private boolean isNumberNext() throws IOException {
		int type = tokenizer.nextToken();
		tokenizer.pushBack();
		return type == StreamTokenizer.TT_WORD;
	}

	/**
	 * Returns the next R_PAREN or COMMA in the stream.
	 *
	 * @param tokenizer
	 *            tokenizer over a stream of text in Well-known Text format. The
	 *            next token must be R_PAREN or COMMA.
	 * @return the next R_PAREN or COMMA in the stream
	 * @throws ParseException
	 *             if the next token is not R_PAREN or COMMA
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@SuppressWarnings("unchecked")
	private Coordinate[] getCoordinates() throws IOException, ParseException {
		String nextToken = getNextEmptyOrOpener();
		if (nextToken.equals(EMPTY)) {
			return new Coordinate[] {};
		}
		@SuppressWarnings("rawtypes")
		ArrayList coordinates = new ArrayList();
		coordinates.add(getPreciseCoordinate());
		nextToken = getNextCloserOrComma();
		while (nextToken.equals(COMMA)) {
			coordinates.add(getPreciseCoordinate());
			nextToken = getNextCloserOrComma();
		}
		Coordinate[] array = new Coordinate[coordinates.size()];
		return (Coordinate[]) coordinates.toArray(array);
	}

	private String getNextCloserOrComma() throws IOException, ParseException {
		String nextWord = getNextWord();
		if (nextWord.equals(COMMA) || nextWord.equals(R_PAREN)) {
			return nextWord;
		}
		parseError(COMMA + " or " + R_PAREN);
		return null;
	}

	/**
	 * Creates a <code>Geometry</code> using the next token in the stream.
	 *
	 * @param tokenizer
	 *            tokenizer over a stream of text in Well-known Text format. The
	 *            next tokens must form a &lt;Geometry Tagged Text&gt;.
	 * @return a <code>Geometry</code> specified by the next token in the stream
	 * @throws ParseException
	 *             if the coordinates used to create a <code>Polygon</code>
	 *             shell and holes do not form closed linestrings, or if an
	 *             unexpected token was encountered
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws org.locationtech.jts.io.ParseException
	 */
	private Object readGeometryTaggedText() throws IOException, ParseException, org.locationtech.jts.io.ParseException {
		String type;

		try {
			type = getNextWord().toUpperCase();
			if (type.endsWith("Z"))
				z = true;
			if (type.endsWith("M"))
				m = true;
		} catch (IOException e) {
			return null;
		} catch (ParseException e) {
			return null;
		}
		if (type.startsWith("MPOINT")) {
			return readMPointText();
		} else if (type.startsWith("MDOUBLE")) {
			return readMDoubleText();
		} else if (type.startsWith("MBOOL")) {
			return readMBoolText();
		} else if (type.startsWith("MINT")) {
			return readMIntText();
		} else if (type.startsWith("MSTRING")) {
			return readMStringText();
		} else if (type.startsWith("MPHOTO")) {
			return readMMPotoText();
		} else if (type.startsWith("MVIDEO")) {
			return readMMVideoText();
		} else if (type.startsWith("MMULTIPOINT")) {
			return readMMultiPointText();
		} else if (type.startsWith("MLINESTRING")) {
			return readMLineStringText();
		} else if (type.startsWith("MPOLYGON")) {
			return readMPolygonStringText();
		} else if (type.startsWith("POINT")) {
			return readPointText();
		} else if (type.startsWith("LINESTRING")) {
			return readLineStringText();
		} else if (type.startsWith("POLYGON")) {
			return readPolygonText();
		} else if (type.startsWith("MINSTANT")) {
			return readMInstantText();
		} else
			parseErrorWithLine("Unknown geometry type: " + type);
		// should never reach here
		return null;
	}

	private MInstant readMInstantText() throws IOException, ParseException{
		String mpString = TempText.replace("MINSTANT ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		long[] times = new long[temp2.length];
		for(int i=0;i<temp2.length;i++)
		{
			times[i] = Long.parseLong(temp2[i]);
		//	System.out.println(times[i]);
		}

		MInstant minstant = geometryFactory.createMInstant(times);
		getNextCloser();
		return minstant;
	}

	private Point readPointText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrOpener();
		if (nextToken.equals(EMPTY)) {
			return geometryFactory2.createPoint((Coordinate) null);
		}
		Point point = geometryFactory2.createPoint(getPreciseCoordinate());
		getNextCloser();
		return point;
	}

	private LineString readLineStringText() throws IOException, ParseException {
		return geometryFactory2.createLineString(getCoordinates());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Polygon readPolygonText() throws IOException, ParseException {
		String nextToken = getNextEmptyOrOpener();
		if (nextToken.equals(EMPTY)) {
			return geometryFactory2.createPolygon(geometryFactory2.createLinearRing(new Coordinate[] {}),
					new LinearRing[] {});
		}
		ArrayList holes = new ArrayList();
		LinearRing shell = readLinearRingText();
		nextToken = getNextCloserOrComma();
		while (nextToken.equals(COMMA)) {
			LinearRing hole = readLinearRingText();
			holes.add(hole);
			nextToken = getNextCloserOrComma();
		}
		LinearRing[] array = new LinearRing[holes.size()];
		return geometryFactory2.createPolygon(shell, (LinearRing[]) holes.toArray(array));
	}

	private LinearRing readLinearRingText() throws IOException, ParseException {
		return geometryFactory2.createLinearRing(getCoordinates());
	}

	private MPoint readMPointText() throws IOException, ParseException {

		String mpString = TempText.replace("MPOINT ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");
		Coordinate[] coords;
		long[] times;
		times = new long[temp2.length];
		coords = new Coordinate[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");

			if (temp3.length == 3) {
				double x = Double.valueOf(temp3[0]);
				double y = Double.valueOf(temp3[1]);
				String time = temp3[2];
				coords[i] = new Coordinate();
				coords[i].x = x;
				coords[i].y = y;
				times[i] = Long.parseLong(time);
			} else if (temp3.length == 4) {
				double x = Double.valueOf(temp3[0]);
				double y = Double.valueOf(temp3[1]);
				double z = Double.valueOf(temp3[2]);
				String time = temp3[3];
				coords[i] = new Coordinate();
				coords[i].x = x;
				coords[i].y = y;
				coords[i].z = z;
				times[i] = Long.parseLong(time);
			}
		}
		MPoint point = geometryFactory.createMPoint(coords, times);
		getNextCloser();
		return point;
	}

	private MDouble readMDoubleText() throws IOException, ParseException {
		String mpString = TempText.replace("MDOUBLE ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		long[] times = new long[temp2.length];
		double[] value = new double[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			double x = Double.valueOf(temp3[0]);
			long time = Long.parseLong(temp3[1]);
			value[i] = x;
			times[i] = time;

		}
		MDouble mdobule = geometryFactory.createMDouble(value, times);
		getNextCloser();
		return mdobule;
	}

	private MBool readMBoolText() throws IOException, ParseException {
		String mpString = TempText.replace("MBOOL ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		long[] times = new long[temp2.length];
		boolean[] bools = new boolean[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			boolean x = Boolean.parseBoolean(temp3[0]);
			long time = Long.parseLong(temp3[1]);
			bools[i] = x;
			times[i] = time;
		}
		MBool mbool = geometryFactory.createMBool(bools, times);
		getNextCloser();
		return mbool;
	}

	private MInt readMIntText() throws IOException, ParseException {
		String mpString = TempText.replace("MINT ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		long[] times = new long[temp2.length];
		int[] count = new int[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			int x = Integer.parseInt(temp3[0]);
			long time = Long.parseLong(temp3[1]);
			count[i] = x;
			times[i] = time;
		}
		MInt mint = geometryFactory.createMInt(count, times);
		getNextCloser();
		return mint;
	}

	private MString readMStringText() throws IOException, org.locationtech.jts.io.ParseException, ParseException {
		String mpString = TempText.replace("MSTRING ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		long[] times = new long[temp2.length];
		String[] string = new String[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			String x = temp3[0];
			long time = Long.parseLong(temp3[1]);
			string[i] = x;
			times[i] = time;
		}
		MString mstring = geometryFactory.createMString(string, times);
		getNextCloser();
		return mstring;
	}

	private MPhoto readMMPotoText() throws IOException, ParseException, org.locationtech.jts.io.ParseException {
		String mpString = TempText.replace("MPHOTO ", "");
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");
		//String[] uri = new String[temp2.length];
		String[] uri = new String[temp2.length];
		double[] width = new double[temp2.length];
		double[] height = new double[temp2.length];
		double[] viewAngle = new double[temp2.length];
		double[] verticalAngle = new double[temp2.length];
		double[] distance = new double[temp2.length];	
		double[] direction = new double[temp2.length];
		double[] direction3d = new double[temp2.length];
		double[] altitude = new double[temp2.length];
		String[] annotationJson = new String[temp2.length];	
		String[] exifJson = new String[temp2.length];
		Coordinate[] coords = new Coordinate[temp2.length];
		long[] creationTime = new long[temp2.length];
		Polygon[] listPolygon = new Polygon[temp2.length];
		FoV[] fov = new FoV[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			uri[i] = temp3[0];
			width[i] = Double.parseDouble(temp3[1]);
			height[i] = Double.parseDouble(temp3[2]);
			viewAngle[i] = Double.parseDouble(temp3[3]);
			verticalAngle[i] = Double.parseDouble(temp3[4]);
			distance[i] = Double.parseDouble(temp3[5]);
			direction[i] = Double.parseDouble(temp3[6]);
			direction3d[i] = Double.parseDouble(temp3[7]);
			altitude[i] = Double.parseDouble(temp3[8]);
			annotationJson[i] = (temp3[9]);
			exifJson[i] =(temp3[10]);
			
			coords[i] = new Coordinate();
			coords[i].x = Double.parseDouble(temp3[11]);
			coords[i].y = Double.parseDouble(temp3[12]);
			creationTime[i] = Long.parseLong(temp3[13]);
			fov[i] = new FoV();
			fov[i].setDirection(Double.parseDouble(temp3[6]));
			fov[i].setDistance(Double.parseDouble(temp3[5]));
			fov[i].setViewAngle(Double.parseDouble(temp3[3]));
			listPolygon[i] = genFoVArea(coords[i].x, coords[i].y, fov[i]);
		}
		MPhoto mphoto = geometryFactory.createMPhoto(uri, width, height,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime, listPolygon, fov);
		getNextCloser();
		return mphoto;
	}

	private MVideo readMMVideoText() throws IOException, ParseException, org.locationtech.jts.io.ParseException {
		String mpString = TempText.replace("MVIDEO ", "");
	//	System.out.println(mpString);
		String temp1 = mpString.replaceAll("\\(|\\)", "");
		String[] temp2 = temp1.split(", ");

		String[] uri = new String[temp2.length];
		double[] viewAngle = new double[temp2.length];
		double[] verticalAngle = new double[temp2.length];
		double[] distance = new double[temp2.length];	
		double[] direction = new double[temp2.length];
		double[] direction3d = new double[temp2.length];
		double[] altitude = new double[temp2.length];
		String[] annotationJson = new String[temp2.length];	
		String[] exifJson = new String[temp2.length];
		Coordinate[] coords = new Coordinate[temp2.length];
		long[] creationTime = new long[temp2.length];
		Polygon[] listPolygon = new Polygon[temp2.length];
		FoV[] fov = new FoV[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split(" ");
			uri[i] = temp3[0];
			viewAngle[i] = Double.parseDouble(temp3[1]);
			verticalAngle[i] = Double.parseDouble(temp3[2]);
			distance[i] = Double.parseDouble(temp3[3]);
			direction[i] = Double.parseDouble(temp3[4]);
			direction3d[i] = Double.parseDouble(temp3[5]);
			altitude[i] = Double.parseDouble(temp3[6]);
			annotationJson[i] = (String.valueOf(temp3[7]));
			exifJson[i] =(String.valueOf(temp3[8]));
			
			coords[i] = new Coordinate();
			coords[i].x = Double.parseDouble(temp3[9]);
			coords[i].y = Double.parseDouble(temp3[10]);
			creationTime[i] = Long.parseLong(temp3[11]);
			fov[i] = new FoV();
			fov[i].setDirection(Double.parseDouble(temp3[4]));
			fov[i].setDistance(Double.parseDouble(temp3[3]));
			fov[i].setViewAngle(Double.parseDouble(temp3[1]));
			fov[i].setCoord(coords[i]);
			listPolygon[i] = genFoVArea(coords[i].x, coords[i].y, fov[i]);
		}
		MVideo mv =  geometryFactory.createMVideo(uri,viewAngle,verticalAngle, distance, direction,direction3d, altitude,annotationJson,exifJson,coords,creationTime, listPolygon, fov);
		getNextCloser();
		return mv;
	}

	private MMultiPoint readMMultiPointText() throws IOException, ParseException {
		String mpString = TempText.replace("MMULTIPOINT ", "");
		// System.out.println(mpString);
		String[] temp1 = mpString.split("\\), \\(");
		Coordinate[] coords;
		long[] times;
		MPoint[] mpoints = new MPoint[temp1.length];

		for (int i = 0; i < temp1.length; i++) {
			String tempMp = temp1[i].replaceAll("\\|\\(", "");
			String[] temp2 = tempMp.split(", ");

			times = new long[temp2.length];
			coords = new Coordinate[temp2.length];
			for (int j = 0; j < temp2.length; j++) {
				String[] temp3 = temp2[j].split(" ");
				double x = Double.valueOf(temp3[0].replaceAll("\\(|\\)", ""));
				double y = Double.valueOf(temp3[1].replaceAll("\\(|\\)", ""));
				String time = temp3[2].replaceAll("\\(|\\)", "");
				coords[j] = new Coordinate();
				coords[j].x = x;
				coords[j].y = y;
				times[j] = Long.parseLong(time);
			}
			mpoints[i] = geometryFactory.createMPoint(coords, times);
		}
		return geometryFactory.createMMultiPoint(mpoints);
	}

	private MLineString readMLineStringText()
			throws ParseException, IOException, org.locationtech.jts.io.ParseException {
		String mpString = TempText.replace("MLINESTRING ", "");
		String temp1 = mpString.substring(1, mpString.length() - 1);
		String[] temp2 = temp1.split(", \\(");
		// MLINESTRING ((10 10, 20 20, 30 40) 10086, (10 10, 20 20, 21 21, 30
		// 40) 10087)
		long[] value = new long[temp2.length];
		LineString[] points = new LineString[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split("\\) ");		
			GeometryFactory geometryFactorys = new GeometryFactory();
			if (i == 0){
				String polyString = temp3[0].substring(1, temp3[0].length());
				String[] poly = polyString.split(", ");
				Coordinate[] coo = new Coordinate[poly.length];
				for(int p=0;p<coo.length;p++)
				{
					double x = Double.parseDouble(poly[p].split(" ")[0]);
					coo[p] = new Coordinate();
					coo[p].x = x;
					coo[p].y= Double.parseDouble(poly[p].split(" ")[1]);
				}
				points[i] = geometryFactorys.createLineString(coo);
			}
			else{
				String polyString = temp3[0];
				String[] poly = polyString.split(", ");
				Coordinate[] coo = new Coordinate[poly.length];
				for(int p=0;p<coo.length;p++)
				{
					coo[p] = new Coordinate();
					coo[p].x= Double.parseDouble(poly[p].split(" ")[0]);
					coo[p].y= Double.parseDouble(poly[p].split(" ")[1]);
				}
				//temp3[0].substring(1, temp3[0].length())
				points[i] = geometryFactorys.createLineString(coo);
			}
			value[i] = Long.parseLong(temp3[1]);
		}
		MLineString mline = geometryFactory.createMLineString(points, value);
		getNextCloser();
		return mline;
	}

	private MPolygon readMPolygonStringText() throws IOException, ParseException {
		String mpString = TempText.replace("MPOLYGON ", "");
		String temp1 = mpString.substring(1, mpString.length() - 1);
		String[] temp2 = temp1.split(", \\(");
		// MLINESTRING ((10 10, 20 20, 30 40) 10086, (10 10, 20 20, 21 21, 30
		// 40) 10087)
		long[] value = new long[temp2.length];
		Polygon[] points = new Polygon[temp2.length];
		for (int i = 0; i < temp2.length; i++) {
			String[] temp3 = temp2[i].split("\\) ");
			PrecisionModel precisionModel = new PrecisionModel(1000);		
			GeometryFactory geometryFactorys = new GeometryFactory(precisionModel, 0);		
			if (i == 0){
				String polyString = temp3[0].substring(1, temp3[0].length());
				String[] poly = polyString.split(", ");
				Coordinate[] coo = new Coordinate[poly.length];
				for(int p=0;p<coo.length;p++)
				{
					double x = Double.parseDouble(poly[p].split(" ")[0]);
					coo[p] = new Coordinate();
					coo[p].x = x;
					coo[p].y= Double.parseDouble(poly[p].split(" ")[1]);
				}
				//temp3[0].substring(1, temp3[0].length())
				points[i] = geometryFactorys.createPolygon(coo);
			}
			else{
				String polyString = temp3[0];
				String[] poly = polyString.split(", ");
				Coordinate[] coo = new Coordinate[poly.length];
				for(int p=0;p<coo.length;p++)
				{
					coo[p] = new Coordinate();
					coo[p].x= Double.parseDouble(poly[p].split(" ")[0]);
					coo[p].y= Double.parseDouble(poly[p].split(" ")[1]);
				}
				//temp3[0].substring(1, temp3[0].length())
				points[i] = geometryFactorys.createPolygon(coo);
			}
			
			value[i] = Long.parseLong(temp3[1]);
		}
		MPolygon mline = geometryFactory.createMPolygon(points, value);
		getNextCloser();
		return mline;
	}

	public String LongToString(long duration) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String timeText = format.format(duration); // long to string
		return timeText;
	}

	public long StringToLong(String s) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date dt2 = sdf.parse(s);
		return dt2.getTime();
	}

	private Polygon genFoVArea(double x, double y, FoV fov){
		int times = 1;
		double x4 = (double) x;
		double y4 = (double) y;
		
		double x2 = (double) x
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.sin(Math.toRadians(fov.getDirection() + (fov.getViewAngle() )/ 2)) * times;
		double y2 = (double) y
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.cos(Math.toRadians(fov.getDirection() +(fov.getViewAngle() )/ 2)) * times;// left
		double x3 = (double) x
				+ fov.getDistance() *  2/Math.sqrt(3) *Math.sin(Math.toRadians(fov.getDirection() - (fov.getViewAngle() ) / 2)) * times;
		double y3 = (double) y
				+ fov.getDistance() * 2/Math.sqrt(3) * Math.cos(Math.toRadians(fov.getDirection() -(fov.getViewAngle() ) / 2)) * times;// right
		GeometryFactory geometryFactory = new GeometryFactory();
		Coordinate[] coor1 = new Coordinate[4];
		coor1[0] = new Coordinate(x4, y4);
		coor1[1] = new Coordinate(x2, y2);
		coor1[2] = new Coordinate(x3, y3);
		coor1[3] = new Coordinate(x4, y4);
		LinearRing line = geometryFactory.createLinearRing(coor1);
		Polygon pl1 = geometryFactory.createPolygon(line, null);
		return pl1;
	}

}
