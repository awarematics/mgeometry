package com.awarematics.postmedia.test;

import java.io.IOException;
import java.text.ParseException;
import com.awarematics.postmedia.io.MWKTReader;
import com.awarematics.postmedia.mgeom.MGeometryFactory;
import com.awarematics.postmedia.operation.SpatialTemporalOP;
import com.awarematics.postmedia.types.mediamodel.MBool;
import com.awarematics.postmedia.types.mediamodel.MDouble;
import com.awarematics.postmedia.types.mediamodel.MGeometry;
import com.awarematics.postmedia.types.mediamodel.MInt;
import com.awarematics.postmedia.types.mediamodel.MLineString;
import com.awarematics.postmedia.types.mediamodel.MMultiPoint;
import com.awarematics.postmedia.types.mediamodel.MPhoto;
import com.awarematics.postmedia.types.mediamodel.MPoint;
import com.awarematics.postmedia.types.mediamodel.MPolygon;
import com.awarematics.postmedia.types.mediamodel.MString;
import com.awarematics.postmedia.types.mediamodel.MVideo;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.WKTReader;

@SuppressWarnings("unused")
public class MWKTReaderTest {

	// private static MPointDistance MPhotoDistance;

	/**
	 * @param args
	 * @throws ParseException
	 * 
	 * @throws IOException
	 * @throws org.locationtech.jts.io.ParseException
	 * @throws org.locationtech.jts.io.ParseException 
	 */
	public static void main(String[] args)
			throws IOException, ParseException, org.locationtech.jts.io.ParseException{

		  //PrecisionModel precisionModel = new PrecisionModel(1000);
		  MGeometryFactory geometryFactory = new MGeometryFactory();
		  MWKTReader reader = new MWKTReader(geometryFactory);	  		  
		/*
		 * MPoint  moving point -----> gps
		 * MPhoto  moving photo -----> exif()
		 */
			MPoint mk2 = (MPoint)reader.read("MPOINT ((12793 1310.2 NaN) 1180338585000");
			
		  
		MPoint mk = (MPoint)reader.read("MPOINT ((4917 11592 NaN) 1182441317000, (4917 11592 NaN) 1182441319000, (4922.69 11585.9 NaN) 1182441321000, (4941.72 11565.6 NaN) 1182441323000, (4968.3 11537.2 NaN) 1182441325000, (4994.89 11508.7 NaN) 1182441327000, (5021.47 11480.3 NaN) 1182441329000, (5044.22 11456 NaN) 1182441331000, (5068.9 11429.6 NaN) 1182441333000, (5088.93 11408.2 NaN) 1182441333000, (5091 11406 NaN) 1182441335000, (5120.64 11381.1 NaN) 1182441337000, (5150.44 11356.1 NaN) 1182441338000, (5166 11343 NaN) 1182441340000, (5195.43 11323.1 NaN) 1182441342000, (5227.65 11301.3 NaN) 1182441344000, (5259.88 11279.5 NaN) 1182441345000, (5268 11274 NaN) 1182441347000, (5299.48 11251.1 NaN) 1182441349000, (5330.97 11228.3 NaN) 1182441351000, (5362.45 11205.4 NaN) 1182441353000, (5393.93 11182.5 NaN) 1182441355000, (5425.42 11159.7 NaN) 1182441357000, (5456.9 11136.8 NaN) 1182441359000, (5488.38 11113.9 NaN) 1182441361000, (5519.87 11091.1 NaN) 1182441362000, (5542 11075 NaN) 1182441364000, (5529.6 11040.1 NaN) 1182441366000, (5516.55 11003.5 NaN) 1182441368000, (5505 10971 NaN) 1182441370000, (5499.97 10932.4 NaN) 1182441372000, (5494.93 10893.8 NaN) 1182441374000, (5489.9 10855.2 NaN) 1182441376000, (5484.87 10816.7 NaN) 1182441377000, (5484 10810 NaN) 1182441379000, (5519.71 10822.2 NaN) 1182441381000, (5556.55 10834.7 NaN) 1182441383000, (5593.39 10847.2 NaN) 1182441385000, (5630.22 10859.8 NaN) 1182441387000, (5667.06 10872.3 NaN) 1182441389000, (5703.9 10884.8 NaN) 1182441391000, (5724.02 10891.7 NaN) 1182441391000, (5725 10892 NaN) 1182441393000, (5745.65 10887.9 NaN) 1182441395000, (5783.82 10880.4 NaN) 1182441397000, (5821.99 10872.8 NaN) 1182441397000, (5826 10872 NaN) 1182441399000, (5845.15 10861.2 NaN) 1182441401000, (5879.03 10842 NaN) 1182441403000, (5912.9 10822.9 NaN) 1182441405000, (5946.77 10803.7 NaN) 1182441407000, (5980.64 10784.6 NaN) 1182441409000, (6014.51 10765.4 NaN) 1182441411000, (6040 10751 NaN) 1182441413000, (6072.79 10730 NaN) 1182441415000, (6105.57 10709.1 NaN) 1182441417000, (6134.28 10690.7 NaN) 1182441417000, (6137 10689 NaN) 1182441419000, (6170.09 10668.5 NaN) 1182441421000, (6203.18 10648.1 NaN) 1182441423000, (6234 10629 NaN) 1182441425000, (6241.77 10626 NaN) 1182441427000, (6267.75 10616 NaN) 1182441429000, (6291.49 10606.8 NaN) 1182441431000, (6327.78 10592.7 NaN) 1182441432000, (6353 10583 NaN) 1182441434000, (6390.14 10571.4 NaN) 1182441436000, (6427.29 10559.8 NaN) 1182441438000, (6464.43 10548.2 NaN) 1182441440000, (6494 10539 NaN) 1182441442000, (6501.59 10535.6 NaN) 1182441444000, (6526.97 10524.1 NaN) 1182441444000, (6536 10520 NaN) 1182441446000, (6519.66 10488.9 NaN) 1182441448000, (6501.57 10454.4 NaN) 1182441449000, (6494 10440 NaN) 1182441451000, (6494.4 10431.7 NaN) 1182441453000, (6495.73 10403.9 NaN) 1182441455000, (6497.59 10365 NaN) 1182441457000, (6499.46 10326.1 NaN) 1182441459000, (6501.32 10287.3 NaN) 1182441460000, (6502 10273 NaN) 1182441462000, (6504.13 10238 NaN) 1182441464000, (6506.5 10199.1 NaN) 1182441466000, (6508.77 10161.9 NaN) 1182441468000, (6510.58 10132.1 NaN) 1182441470000, (6512.55 10099.7 NaN) 1182441471000, (6514 10076 NaN) 1182441473000, (6516.43 10037.2 NaN) 1182441475000, (6518.85 9998.33 NaN) 1182441477000, (6521.28 9959.5 NaN) 1182441478000, (6522 9948 NaN) 1182441480000, (6520.2 9909.13 NaN) 1182441482000, (6518.41 9870.26 NaN) 1182441484000, (6516.61 9831.39 NaN) 1182441486000, (6514.81 9792.52 NaN) 1182441487000, (6514 9775 NaN) 1182441489000, (6501.09 9747.34 NaN) 1182441491000, (6484.64 9712.08 NaN) 1182441493000, (6468.18 9676.82 NaN) 1182441494000, (6458 9655 NaN) 1182441496000, (6458.66 9616.1 NaN) 1182441498000, (6459.32 9577.19 NaN) 1182441500000, (6459.98 9538.3 NaN) 1182441500000, (6460 9537 NaN) 1182441502000, (6460.33 9498.09 NaN) 1182441504000, (6460.66 9459.18 NaN) 1182441506000, (6460.99 9420.27 NaN) 1182441508000, (6461.32 9381.36 NaN) 1182441510000, (6461.64 9342.45 NaN) 1182441512000, (6461.97 9303.55 NaN) 1182441514000, (6462.3 9264.64 NaN) 1182441516000, (6462.63 9225.73 NaN) 1182441518000, (6462.95 9188.37 NaN) 1182441519000, (6463 9182 NaN) 1182441521000, (6463.62 9143.09 NaN) 1182441523000, (6464.24 9104.19 NaN) 1182441525000, (6464.85 9065.28 NaN) 1182441527000, (6465.47 9026.38 NaN) 1182441529000, (6466.06 8989.07 NaN) 1182441531000, (6466.64 8952.4 NaN) 1182441532000, (6467 8930 NaN) 1182441534000, (6467.79 8891.1 NaN) 1182441536000, (6468.58 8852.19 NaN) 1182441538000, (6469.32 8815.39 NaN) 1182441539000, (6470 8782 NaN) 1182441541000, (6470 8762.74 NaN) 1182441543000, (6470 8725.46 NaN) 1182441545000, (6470 8686.55 NaN) 1182441546000, (6470 8681 NaN) 1182441548000, (6484.71 8655.01 NaN) 1182441550000, (6490.44 8644.9 NaN) 1182441552000, (6502.41 8623.74 NaN) 1182441554000, (6521.58 8589.88 NaN) 1182441556000, (6540.75 8556.01 NaN) 1182441558000, (6559.91 8522.15 NaN) 1182441560000, (6579.08 8488.29 NaN) 1182441561000, (6590 8469 NaN) 1182441563000, (6620.71 8445.11 NaN) 1182441565000, (6651.43 8421.22 NaN) 1182441567000, (6682.14 8397.33 NaN) 1182441568000, (6698 8385 NaN) 1182441570000, (6729.92 8362.75 NaN) 1182441572000, (6758.69 8342.68 NaN) 1182441574000, (6787.37 8322.69 NaN) 1182441575000, (6807 8309 NaN) 1182441577000, (6837.74 8285.14 NaN) 1182441579000, (6868.47 8261.28 NaN) 1182441581000, (6899.21 8237.42 NaN) 1182441583000, (6920.2 8221.12 NaN) 1182441585000, (6947.32 8200.06 NaN) 1182441586000, (6959 8191 NaN) 1182441588000, (6988.05 8165.11 NaN) 1182441589000, (7005 8150 NaN) 1182441591000, (7032.18 8122.16 NaN) 1182441593000, (7059.36 8094.31 NaN) 1182441595000, (7086.53 8066.48 NaN) 1182441595000, (7087 8066 NaN) 1182441597000, (7113.25 8037.28 NaN) 1182441599000, (7139.5 8008.55 NaN) 1182441601000, (7165.74 7979.83 NaN) 1182441603000, (7191.99 7951.1 NaN) 1182441603000, (7193 7950 NaN) 1182441605000, (7218.99 7921.04 NaN) 1182441607000, (7244.97 7892.08 NaN) 1182441609000, (7270.96 7863.12 NaN) 1182441611000, (7296.94 7834.15 NaN) 1182441613000, (7322.93 7805.19 NaN) 1182441614000, (7324 7804 NaN) 1182441616000, (7354.79 7780.21 NaN) 1182441618000, (7385.59 7756.43 NaN) 1182441620000, (7416.38 7732.64 NaN) 1182441622000, (7447.17 7708.85 NaN) 1182441624000, (7477.97 7685.07 NaN) 1182441624000, (7491 7675 NaN) 1182441626000, (7525 7656.08 NaN) 1182441628000, (7559 7637.16 NaN) 1182441630000, (7593 7618.23 NaN) 1182441631000, (7606 7611 NaN) 1182441633000, (7641.83 7595.83 NaN) 1182441635000, (7677.66 7580.66 NaN) 1182441637000, (7713.49 7565.49 NaN) 1182441637000, (7717 7564 NaN) 1182441639000, (7751.1 7545.27 NaN) 1182441641000, (7785.21 7526.53 NaN) 1182441643000, (7819.31 7507.8 NaN) 1182441645000, (7853.42 7489.07 NaN) 1182441646000, (7859 7486 NaN) 1182441648000, (7892.64 7466.45 NaN) 1182441650000, (7926.29 7446.9 NaN) 1182441650000, (7933 7443 NaN) 1182441652000, (7963.16 7418.41 NaN) 1182441654000, (7982.92 7402.3 NaN) 1182441655000, (7998 7390 NaN) 1182441657000, (8019.37 7357.49 NaN) 1182441659000, (8040.75 7324.97 NaN) 1182441661000, (8059.47 7296.5 NaN) 1182441663000, (8079.85 7265.49 NaN) 1182441665000, (8101.23 7232.97 NaN) 1182441667000, (8122.6 7200.46 NaN) 1182441669000, (8140 7174 NaN) 1182441671000, (8167.51 7146.49 NaN) 1182441673000, (8195.03 7118.97 NaN) 1182441673000, (8203 7111 NaN) 1182441675000, (8238.49 7095.05 NaN) 1182441677000, (8273.98 7079.09 NaN) 1182441679000, (8309.36 7063.18 NaN) 1182441680000, (8312 7062 NaN) 1182441682000, (8334.16 7045.91 NaN) 1182441684000, (8344.09 7038.71 NaN) 1182441686000, (8372.77 7017.88 NaN) 1182441688000, (8399.84 6998.24 NaN) 1182441690000, (8430.38 6976.06 NaN) 1182441691000, (8447 6964 NaN) 1182441693000, (8475.57 6937.59 NaN) 1182441695000, (8500.48 6914.57 NaN) 1182441697000, (8528.95 6888.26 NaN) 1182441699000, (8557.53 6861.85 NaN) 1182441701000, (8586.1 6835.44 NaN) 1182441703000, (8614.68 6809.02 NaN) 1182441705000, (8643.24 6782.63 NaN) 1182441705000, (8645 6781 NaN) 1182441707000, (8673.02 6754 NaN) 1182441709000, (8701.03 6726.99 NaN) 1182441711000, (8728 6701 NaN) 1182441713000, (8751.98 6686.98 NaN) 1182441715000, (8775.95 6672.95 NaN) 1182441717000, (8799.93 6658.93 NaN) 1182441719000, (8823.91 6644.9 NaN) 1182441720000, (8834 6639 NaN) 1182441722000, (8852.02 6640.06 NaN) 1182441724000, (8879.75 6641.7 NaN) 1182441726000, (8907.48 6643.33 NaN) 1182441728000, (8935.21 6644.97 NaN) 1182441730000, (8962.94 6646.6 NaN) 1182441732000, (8990.67 6648.24 NaN) 1182441734000, (9018.4 6649.87 NaN) 1182441736000, (9046.13 6651.51 NaN) 1182441738000, (9073.86 6653.14 NaN) 1182441740000, (9101.59 6654.78 NaN) 1182441742000, (9125.04 6656.16 NaN) 1182441744000, (9152.77 6657.8 NaN) 1182441746000, (9180.5 6659.43 NaN) 1182441748000, (9208.22 6661.07 NaN) 1182441750000, (9235.95 6662.7 NaN) 1182441751000, (9241 6663 NaN) 1182441753000, (9241 6654.67 NaN) 1182441755000, (9241 6626.81 NaN) 1182441757000, (9241 6587.9 NaN) 1182441759000, (9241 6548.99 NaN) 1182441759000, (9241 6542 NaN) 1182441761000, (9237.23 6503.27 NaN) 1182441763000, (9233.46 6464.54 NaN) 1182441765000, (9229.7 6425.82 NaN) 1182441767000, (9225.93 6387.09 NaN) 1182441769000, (9223 6357 NaN) 1182441771000, (9219.28 6318.27 NaN) 1182441773000, (9215.55 6279.54 NaN) 1182441775000, (9211.83 6240.8 NaN) 1182441777000, (9208.1 6202.07 NaN) 1182441779000, (9204.38 6163.34 NaN) 1182441781000, (9200.65 6124.61 NaN) 1182441782000, (9198 6097 NaN) 1182441784000, (9193.36 6058.37 NaN) 1182441786000, (9188.73 6019.73 NaN) 1182441788000, (9184.09 5981.1 NaN) 1182441790000, (9179.46 5942.47 NaN) 1182441791000, (9177 5922 NaN) 1182441793000, (9173.03 5883.29 NaN) 1182441795000, (9169.06 5844.59 NaN) 1182441797000, (9165.09 5805.9 NaN) 1182441797000, (9165 5805 NaN) 1182441799000, (9162.42 5766.18 NaN) 1182441801000, (9159.84 5727.35 NaN) 1182441803000, (9157.26 5688.53 NaN) 1182441805000, (9154.68 5649.7 NaN) 1182441807000, (9152.11 5610.88 NaN) 1182441809000, (9149.53 5572.05 NaN) 1182441811000, (9147 5534 NaN) 1182441813000, (9148.36 5525.78 NaN) 1182441815000, (9152.9 5498.3 NaN) 1182441817000, (9159.24 5459.91 NaN) 1182441819000, (9165.58 5421.52 NaN) 1182441821000, (9171.92 5383.13 NaN) 1182441823000, (9178.26 5344.74 NaN) 1182441825000, (9184 5310 NaN) 1182441827000, (9185 5271.14 NaN) 1182441827000, (9185 5271 NaN) 1182441829000, (9186.79 5232.13 NaN) 1182441831000, (9188.57 5193.26 NaN) 1182441831000, (9189 5184 NaN) 1182441833000, (9193.69 5145.37 NaN) 1182441835000, (9198.38 5106.75 NaN) 1182441837000, (9203.07 5068.12 NaN) 1182441839000, (9207.76 5029.49 NaN) 1182441841000, (9212.44 4990.87 NaN) 1182441843000, (9215.29 4967.39 NaN) 1182441845000, (9219.26 4934.74 NaN) 1182441847000, (9223.95 4896.11 NaN) 1182441849000, (9228.63 4857.49 NaN) 1182441851000, (9233.32 4818.86 NaN) 1182441853000, (9238.01 4780.23 NaN) 1182441855000, (9242.7 4741.61 NaN) 1182441857000, (9247.39 4702.98 NaN) 1182441859000, (9252 4665 NaN) 1182441861000, (9257.41 4626.47 NaN) 1182441863000, (9262.81 4587.93 NaN) 1182441865000, (9268.22 4549.4 NaN) 1182441867000, (9273.62 4510.87 NaN) 1182441869000, (9279.03 4472.33 NaN) 1182441871000, (9284.43 4433.8 NaN) 1182441873000, (9289.28 4399.28 NaN) 1182441874000, (9291 4387 NaN) 1182441876000, (9297.97 4348.72 NaN) 1182441878000, (9304.95 4310.44 NaN) 1182441880000, (9311.92 4272.16 NaN) 1182441882000, (9318.89 4233.88 NaN) 1182441884000, (9325.87 4195.6 NaN) 1182441886000, (9332.84 4157.32 NaN) 1182441888000, (9338 4129 NaN) 1182441890000, (9346.15 4090.95 NaN) 1182441892000, (9354.31 4052.91 NaN) 1182441894000, (9360.7 4023.06 NaN) 1182441896000, (9368.85 3985.01 NaN) 1182441898000, (9377.01 3946.97 NaN) 1182441900000, (9385.16 3908.92 NaN) 1182441902000, (9393.31 3870.87 NaN) 1182441904000, (9401.47 3832.82 NaN) 1182441906000, (9409.62 3794.79 NaN) 1182441906000, (9410 3793 NaN) 1182441908000, (9418.88 3755.12 NaN) 1182441910000, (9427.76 3717.23 NaN) 1182441912000, (9436.64 3679.35 NaN) 1182441913000, (9440 3665 NaN) 1182441915000, (9451.53 3627.84 NaN) 1182441917000, (9463.06 3590.67 NaN) 1182441919000, (9473.96 3555.52 NaN) 1182441921000, (9485.49 3518.35 NaN) 1182441923000, (9497.02 3481.19 NaN) 1182441925000, (9508.55 3444.03 NaN) 1182441927000, (9520.08 3406.86 NaN) 1182441929000, (9531.61 3369.7 NaN) 1182441931000, (9543.13 3332.54 NaN) 1182441933000, (9552.24 3303.17 NaN) 1182441935000, (9559.74 3279 NaN) 1182441937000, (9571.25 3241.91 NaN) 1182441939000, (9582.78 3204.75 NaN) 1182441941000, (9594.3 3167.59 NaN) 1182441942000, (9601 3146 NaN) 1182441944000, (9614.53 3109.52 NaN) 1182441946000, (9628.06 3073.03 NaN) 1182441948000, (9641.58 3036.55 NaN) 1182441950000, (9655.11 3000.07 NaN) 1182441952000, (9668.64 2963.58 NaN) 1182441954000, (9682.17 2927.1 NaN) 1182441956000, (9695.69 2890.62 NaN) 1182441957000, (9700 2879 NaN) 1182441959000, (9706.96 2861.04 NaN) 1182441961000, (9720.42 2826.28 NaN) 1182441963000, (9734.48 2790 NaN) 1182441965000, (9748.53 2753.71 NaN) 1182441967000, (9759.11 2726.41 NaN) 1182441969000, (9771.85 2693.52 NaN) 1182441971000, (9785.91 2657.24 NaN) 1182441971000, (9786 2657 NaN) 1182441973000, (9799.92 2620.66 NaN) 1182441975000, (9813.84 2584.33 NaN) 1182441977000, (9827.76 2547.99 NaN) 1182441979000, (9841.68 2511.66 NaN) 1182441981000, (9855.6 2475.32 NaN) 1182441983000, (9869.52 2438.99 NaN) 1182441985000, (9883.44 2402.65 NaN) 1182441987000, (9897.36 2366.32 NaN) 1182441989000, (9911.28 2329.98 NaN) 1182441990000, (9922 2302 NaN) 1182441992000, (9936.1 2265.74 NaN) 1182441993000, (9943 2248 NaN) 1182441995000, (9956.11 2211.36 NaN) 1182441997000, (9969.22 2174.73 NaN) 1182441999000, (9982.33 2138.09 NaN) 1182442001000, (9995.44 2101.46 NaN) 1182442003000, (10008.5 2064.82 NaN) 1182442005000, (10021.7 2028.19 NaN) 1182442007000, (10034.8 1991.55 NaN) 1182442009000, (10045.6 1961.16 NaN) 1182442011000, (10055 1935 NaN) 1182442013000, (10068.3 1898.44 NaN) 1182442015000, (10081.6 1861.88 NaN) 1182442017000, (10095 1825.32 NaN) 1182442019000, (10107.4 1791.19 NaN) 1182442021000, (10120.6 1754.9 NaN) 1182442023000, (10133.9 1718.34 NaN) 1182442025000, (10147.2 1681.78 NaN) 1182442027000, (10160.6 1645.22 NaN) 1182442029000, (10173.9 1608.66 NaN) 1182442031000, (10187.2 1572.1 NaN) 1182442033000, (10200.5 1535.54 NaN) 1182442035000, (10210.9 1507.06 NaN) 1182442037000, (10211.6 1505.23 NaN) 1182442038000, (10212 1504 NaN) 1182442040000, (10216.1 1492.72 NaN) 1182442042000, (10227.9 1460.53 NaN) 1182442044000, (10241.3 1423.99 NaN) 1182442046000, (10254.7 1387.46 NaN) 1182442048000, (10268.1 1350.92 NaN) 1182442050000, (10281.5 1314.39 NaN) 1182442052000, (10294.9 1277.86 NaN) 1182442054000, (10308.3 1241.32 NaN) 1182442055000, (10315 1223 NaN) 1182442057000, (10328.6 1186.55 NaN) 1182442059000, (10342.2 1150.1 NaN) 1182442061000, (10355.8 1113.65 NaN) 1182442063000, (10369.5 1077.2 NaN) 1182442065000, (10380.8 1046.96 NaN) 1182442067000, (10394.4 1010.51 NaN) 1182442069000, (10408 974.061 NaN) 1182442070000, (10411 966 NaN) 1182442072000, (10403.3 962.771 NaN) 1182442074000, (10381.4 953.541 NaN) 1182442076000, (10355.8 942.777 NaN) 1182442078000, (10330.1 932.014 NaN) 1182442080000, (10304.5 921.25 NaN) 1182442082000, (10278.9 910.487 NaN) 1182442084000, (10261.1 902.975 NaN) 1182442086000, (10249.1 897.957 NaN) 1182442088000, (10223.7 887.264 NaN) 1182442090000, (10198.1 876.5 NaN) 1182442092000, (10172.5 865.737 NaN) 1182442094000, (10146.9 854.973 NaN) 1182442096000, (10121.3 844.209 NaN) 1182442098000, (10095.6 833.446 NaN) 1182442100000, (10070 822.682 NaN) 1182442102000, (10048.3 813.54 NaN) 1182442102000, (10047 813 NaN) 1182442104000, (10040.5 797.641 NaN) 1182442106000, (10036.6 788.218 NaN) 1182442108000, (10030.3 773.365 NaN) 1182442110000, (10023.8 758.005 NaN) 1182442112000, (10017.4 742.646 NaN) 1182442114000, (10010.9 727.287 NaN) 1182442116000, (10004.4 711.927 NaN) 1182442118000, (9998.94 698.905 NaN) 1182442120000, (9992.78 684.28 NaN) 1182442122000, (9986.31 668.921 NaN) 1182442124000, (9981.08 656.513 NaN) 1182442126000, (9974.66 641.274 NaN) 1182442128000, (9968.19 625.914 NaN) 1182442130000, (9961.72 610.555 NaN) 1182442132000, (9955.25 595.195 NaN) 1182442134000, (9948.78 579.836 NaN) 1182442136000, (9942.31 564.476 NaN) 1182442137000, (9940 559 NaN) 1182442139000, (9936.63 551.377 NaN) 1182442141000, (9930.07 536.512 NaN) 1182442143000, (9923.34 521.266 NaN) 1182442145000, (9916.6 506.02 NaN) 1182442147000, (9910.98 493.289 NaN) 1182442149000, (9906.67 483.531 NaN) 1182442151000, (9899.94 468.285 NaN) 1182442153000, (9893.21 453.039 NaN) 1182442155000, (9886.47 437.793 NaN) 1182442157000, (9879.74 422.547 NaN) 1182442159000, (9873.01 407.301 NaN) 1182442161000, (9866.27 392.054 NaN) 1182442163000, (9859.54 376.808 NaN) 1182442165000, (9854.46 365.297 NaN) 1182442167000, (9851.5 358.589 NaN) 1182442169000, (9844.92 343.701 NaN) 1182442171000, (9838.19 328.455 NaN) 1182442173000, (9831.46 313.209 NaN) 1182442175000, (9824.72 297.963 NaN) 1182442177000, (9817.99 282.717 NaN) 1182442179000, (9811.26 267.47 NaN) 1182442181000, (9804.52 252.224 NaN) 1182442183000, (9797.79 236.978 NaN) 1182442185000, (9791.06 221.732 NaN) 1182442187000, (9784.32 206.486 NaN) 1182442189000, (9777.59 191.24 NaN) 1182442191000, (9770.86 175.993 NaN) 1182442193000, (9764.13 160.747 NaN) 1182442195000, (9757.39 145.501 NaN) 1182442197000, (9750.66 130.255 NaN) 1182442199000, (9743.93 115.009 NaN) 1182442201000, (9737.19 99.7626 NaN) 1182442202000, (9732 88 NaN) 1182442204000, (9730.15 71.4367 NaN) 1182442206000, (9728.29 54.8734 NaN) 1182442208000, (9726.77 41.2915 NaN) 1182442210000, (9725.56 30.4425 NaN) 1182442212000, (9723.71 13.8792 NaN) 1182442214000, (9721.85 -2.68413 NaN) 1182442216000, (9720 -19.2474 NaN) 1182442218000, (9718.15 -35.8108 NaN) 1182442220000, (9716.29 -52.3743 NaN) 1182442221000)");
		
		System.out.println(mk.getCoords()[0]);
		//System.out.println(mk.toGeoString()); // OK
		SpatialTemporalOP l ;
		l = new SpatialTemporalOP();
		l.m_eventPosition(mk.toGeoString(), "POINT(40.7780543994538 -73.9516338520441)");
		
		//System.out.println(mk.numOf());  // OK
		
		//long queryTime = 1481480638000L;
		//Point p = (Point)mk.snapshot(queryTime);

		//System.out.println(p.toText());
		

		//long x = 1000;
		//System.out.println(mk.atomize( x).toGeoString());  //return    each point by x times decade
		//System.out.println(mk.lattice( x).toGeoString());  //return    each point nearest time by x times decade 
		
		//System.out.println(mk.getDuration());
		
		//long start = 1481480635000L;
		//long end = 1481480645000L;
		//System.out.println(mk.slice(start, end).toGeoString());
		//System.out.println("--------------------------------");
		
		/* int n=5;
		 System.out.println(mp.first(mp));
		 System.out.println(mp.last(mp));
		 System.out.println(mp.at(mp,n));
		 System.out.println(mp.numOf(mp));
		
	/*	v 
		
		MLineString ml = new MLineString ("MLINESTRING (0 0 2016-12-12 03:23:52.000, 2 5 2016-12-12 03:23:57.123)");
		System.out.println(ml.toGeoString());
		
		
		MDouble md = (MDouble) reader.read("MDOUBLE (39.1 1481480632000, 34.3 1481480633000, 41.2 1481480635000)" );
		System.out.println(md.toGeoString());
		MBool mb = (MBool)reader.read( "MBOOL (true 1481480632000, false 1481480633000, false 1481480634000, true 1481480635000)");//judge  time
		System.out.println(mb.toGeoString());
		MInt mi = (MInt)reader.read( "MINT (10 1481480632000, 15 1481480633000, 20 1481480634000, 17 1481480636000)");  // number  time 
		System.out.println(mi.toGeoString());
		MString ms = (MString)reader.read("MSTRING ('Augustus' 1181480638000, 'Nero' 1431480638000, 'Constantine' 1981480638000)");// person   time
		System.out.println(ms.toGeoString());
*/
		//(uri, hangle, vangle, distance, direction, attitude, x, y),time
	/*	MPhoto  mphoto = (MPhoto)reader.read( "MPHOTO (('https://github.com/awarematics/mgeometry/test.jpg' 120 120 30 3 200 100 100) 1981480638000, ('https://github.com/awarematics/mgeometry/test2.jpg' 120 120 30 3 100 101 101) 1981480639000)");
		System.out.println(mphoto.toGeoString());
		*///MPoint  mv = (MPoint)reader.read( "MPOINT ((40.7780543994538 -73.9516338520441) 1504354462000, (40.7780543994538 -73.9516338520441) 1504354463000, (40.7780543994538 -73.9516338520441) 1504354464000, (40.7780543994538 -73.9516338520441) 1504354465000, (40.7780543994538 -73.9516338520441) 1504354466000, (40.7780543994538 -73.9516338520441) 1504354467000, (40.7780543994538 -73.9516338520441) 1504354468000, (40.7780543994538 -73.9516338520441) 1504354469000, (40.7780543994538 -73.9516338520441) 1504354470000, (40.7780479034789 -73.9516391326431) 1504354471000, (40.7780341990672 -73.9516503643933) 1504354472000, (40.7780073350675 -73.9516702295038) 1504354473000, (40.7779640844472 -73.9517035056594) 1504354474000, (40.7779178582512 -73.9517416433189) 1504354475000, (40.7778477855407 -73.9517926052901) 1504354476000, (40.7777769165493 -73.9518498536888) 1504354477000, (40.7776938099794 -73.9519182500187) 1504354478000, (40.77759766955 -73.9519953635279) 1504354479000, (40.7775085280098 -73.9520645980481) 1504354480000, (40.7774200151123 -73.9521337487492) 1504354481000, (40.7773641497277 -73.9521774184647) 1504354482000, (40.7773304125674 -73.952204575831) 1504354483000, (40.7773152832322 -73.9522146341148) 1504354484000, (40.7773115113757 -73.9522165619526) 1504354485000, (40.7773023751013 -73.9522234351132) 1504354486000, (40.7772739604495 -73.9522432164046) 1504354487000, (40.7772347750522 -73.9522707928661) 1504354488000, (40.7771758502729 -73.9523118641916) 1504354489000, (40.777107286305 -73.9523591381255) 1504354490000, (40.7770381356038 -73.9524100162778) 1504354491000, (40.7769596390806 -73.9524704497996) 1504354492000, (40.776900043749 -73.9525179751906) 1504354493000, (40.7768346229948 -73.9525671769622) 1504354494000, (40.7767769135914 -73.9526084997449) 1504354495000, (40.77672163494 -73.9526499063465) 1504354496000, (40.7766538253434 -73.952698689023) 1504354497000, (40.7765922602646 -73.952743616024) 1504354498000, (40.776516655498 -73.9527978469375) 1504354499000, (40.7764433976643 -73.9528517425749) 1504354500000, (40.7763731154062 -73.9529033750984) 1504354501000)");
	//	System.out.println(mv.toGeoString());
	//	MVideo mp = (MVideo)reader.read("MVIDEO ((0000f77c-62c2a288.mov?t=1 60 -1 0.001 -15.5971811517671 -1 -1 'null' 'null' 40.6775779277465 -73.8310543914082) 1503828255000, (0000f77c-62c2a288.mov?t=2 60 -1 0.001 -15.5971811517671 -1 -1 'null' 'null' 40.6774585275358 -73.831088170478) 1503828256000, (0000f77c-62c2a288.mov?t=3 60 -1 0.001 -15.0252048788381 -1 -1 'null' 'null' 40.6773299072317 -73.8311231230142) 1503828257000, (0000f77c-62c2a288.mov?t=4 60 -1 0.001 -14.2325586776312 -1 -1 'null' 'null' 40.6771981856233 -73.831156902084) 1503828258000, (0000f77c-62c2a288.mov?t=5 60 -1 0.001 -8.93153565382268 -1 -1 'null' 'null' 40.6770616863302 -73.8311784435751) 1503828259000, (0000f77c-62c2a288.mov?t=6 60 -1 0.001 -4.27087267612698 -1 -1 'null' 'null' 40.6769282464317 -73.8311884180399) 1503828260000, (0000f77c-62c2a288.mov?t=7 60 -1 0.001 -2.55530222803052 -1 -1 'null' 'null' 40.6768062059215 -73.8311938662769) 1503828261000, (0000f77c-62c2a288.mov?t=8 60 -1 0.001 -4.99489993530309 -1 -1 'null' 'null' 40.6767075509212 -73.8312024996372) 1503828262000, (0000f77c-62c2a288.mov?t=9 60 -1 0.001 -8.34463727316125 -1 -1 'null' 'null' 40.6766346702731 -73.8312132284733) 1503828263000, (0000f77c-62c2a288.mov?t=10 60 -1 0.001 -11.2366268380858 -1 -1 'null' 'null' 40.6765768351412 -73.8312247954997) 1503828264000, (0000f77c-62c2a288.mov?t=11 60 -1 0.001 -12.8961427109887 -1 -1 'null' 'null' 40.6765227718658 -73.8312372845354) 1503828265000, (0000f77c-62c2a288.mov?t=12 60 -1 0.001 -14.5312825215844 -1 -1 'null' 'null' 40.6764565967402 -73.8312546350749) 1503828266000, (0000f77c-62c2a288.mov?t=13 60 -1 0.001 -17.6718213833648 -1 -1 'null' 'null' 40.676376717203 -73.8312805351557) 1503828267000, (0000f77c-62c2a288.mov?t=14 60 -1 0.001 -22.4188574000815 -1 -1 'null' 'null' 40.6762848515442 -73.8313195948245) 1503828268000, (0000f77c-62c2a288.mov?t=15 60 -1 0.001 -27.1214137173315 -1 -1 'null' 'null' 40.67618393343 -73.831373825738) 1503828269000, (0000f77c-62c2a288.mov?t=16 60 -1 0.001 -29.9228346952912 -1 -1 'null' 'null' 40.6760758068791 -73.8314400427731) 1503828270000, (0000f77c-62c2a288.mov?t=17 60 -1 0.001 -33.6361977474 -1 -1 'null' 'null' 40.6759702787182 -73.8315165695491) 1503828271000, (0000f77c-62c2a288.mov?t=18 60 -1 0.001 -36.4648080902999 -1 -1 'null' 'null' 40.6758701149753 -73.8315992151143) 1503828272000, (0000f77c-62c2a288.mov?t=19 60 -1 0.001 -41.0108335048101 -1 -1 'null' 'null' 40.675779506602 -73.8316920866015) 1503828273000, (0000f77c-62c2a288.mov?t=20 60 -1 0.001 -44.5667449160952 -1 -1 'null' 'null' 40.6757038599259 -73.8317857124599) 1503828274000, (0000f77c-62c2a288.mov?t=21 60 -1 0.001 -47.9767108203941 -1 -1 'null' 'null' 40.6756510539359 -73.8318665978255) 1503828275000, (0000f77c-62c2a288.mov?t=22 60 -1 0.001 -50.1767426393922 -1 -1 'null' 'null' 40.6756235612935 -73.8319164701494) 1503828276000, (0000f77c-62c2a288.mov?t=23 60 -1 0.001 -50.0646468201356 -1 -1 'null' 'null' 40.675610359796 -73.8319401909353) 1503828277000, (0000f77c-62c2a288.mov?t=24 60 -1 0.001 -50.9788384598721 -1 -1 'null' 'null' 40.6755987927696 -73.8319627382549) 1503828278000, (0000f77c-62c2a288.mov?t=25 60 -1 0.001 -51.8618651597633 -1 -1 'null' 'null' 40.6755787181115 -73.832005485961) 1503828279000, (0000f77c-62c2a288.mov?t=26 60 -1 0.001 -52.7848207720977 -1 -1 'null' 'null' 40.6755518960214 -73.8320690207871) 1503828280000, (0000f77c-62c2a288.mov?t=27 60 -1 0.001 -52.8098779896296 -1 -1 'null' 'null' 40.6755173625803 -73.8321510796191) 1503828281000, (0000f77c-62c2a288.mov?t=28 60 -1 0.001 -53.1082970113209 -1 -1 'null' 'null' 40.675476877988 -73.832251075724) 1503828282000, (0000f77c-62c2a288.mov?t=29 60 -1 0.001 -53.068188778856 -1 -1 'null' 'null' 40.6754317414394 -73.8323619683029) 1503828283000, (0000f77c-62c2a288.mov?t=30 60 -1 0.001 -53.0305369542053 -1 -1 'null' 'null' 40.6753836712247 -73.8324794825854) 1503828284000, (0000f77c-62c2a288.mov?t=31 60 -1 0.001 -53.3029420481242 -1 -1 'null' 'null' 40.6753343018151 -73.8326047082188) 1503828285000, (0000f77c-62c2a288.mov?t=32 60 -1 0.001 -53.0197532025893 -1 -1 'null' 'null' 40.6752801547206 -73.8327368908318) 1503828286000, (0000f77c-62c2a288.mov?t=33 60 -1 0.001 -52.5874628391729 -1 -1 'null' 'null' 40.6752199726558 -73.8328760304245) 1503828287000, (0000f77c-62c2a288.mov?t=34 60 -1 0.001 -51.9302203881571 -1 -1 'null' 'null' 40.6751535460732 -73.8330185227784) 1503828288000, (0000f77c-62c2a288.mov?t=35 60 -1 0.001 -50.922166841815 -1 -1 'null' 'null' 40.6750807911536 -73.8331595902088) 1503828289000, (0000f77c-62c2a288.mov?t=36 60 -1 0.001 -47.9597861934417 -1 -1 'null' 'null' 40.6749968044839 -73.8332880847844) 1503828290000, (0000f77c-62c2a288.mov?t=37 60 -1 0.001 -43.3243365064324 -1 -1 'null' 'null' 40.6749011250592 -73.8333986420872) 1503828291000, (0000f77c-62c2a288.mov?t=38 60 -1 0.001 -38.9440321737091 -1 -1 'null' 'null' 40.6747988239309 -73.8334934414121) 1503828292000, (0000f77c-62c2a288.mov?t=39 60 -1 0.001 -34.9974711565284 -1 -1 'null' 'null' 40.6746977800882 -73.8335713931116) 1503828293000, (0000f77c-62c2a288.mov?t=40 60 -1 0.001 -30.9875643183179 -1 -1 'null' 'null' 40.6746057467914 -73.833630569348) 1503828294000, (0000f77c-62c2a288.mov?t=41 60 -1 0.001 -27.4478605126405 -1 -1 'null' 'null' 40.6745297229296 -73.8336720597687) 1503828295000)");
		//System.out.println(SpatialTemporalOP.M_Relationship(mp.toGeoString(), mv.toGeoString()));
	/*	MMultiPoint mmp = (MMultiPoint)reader.read("MMULTIPOINT (((3 6) 1481480632000, (111 333) 1481480633000, (0 2) 1481480634000, (2 7) 1481480635000, (33 93) 1481480636000), ((3 6) 1481480632000, (111 333) 1481480633000, (1 3) 1481480634000, (3 8) 1481480635000, (30 93) 1481480636000))") ;// (MPoint1), (MPoint2)
		System.out.println(mmp.toGeoString());
		
		MLineString ml = (MLineString)reader.read( "MLINESTRING ((10 10, 20 20, 30 40) 1481480635000, (10 20, 20 20, 21 21, 30 40) 1481480636000, (10 30, 20 20, 21 21, 30 50) 1481480637000)");// point  time, point time// To be implemented		
		System.out.println(ml.toGeoString());
		MPolygon mpol = (MPolygon)reader.read( "MPOLYGON ((10 10, 10 20, 20 20, 20 15, 10 10) 1481480635000, (10 30, 10 20, 20 30, 20 15, 10 30) 1481480635000, (10 20, 10 20, 20 20, 20 15, 10 20) 1481480635000, (20 10, 10 20, 20 30, 20 15, 10 50, 20 10) 1481480635000)");  // To be implemented*/
	/*	System.out.println(mpol.toGeoString());
		
		
		 GeometryFactory geometryFactorys = new GeometryFactory(precisionModel, 0);
		 WKTReader readers = new WKTReader( geometryFactorys );
		LineString geometry1 = (LineString) readers.read("LINESTRING(0 0, 2 0, 5 0)");
        LineString geometry2 = (LineString) readers.read("LINESTRING(0 0, 0 2, 5 0)");
        Geometry interPoint = geometry1.intersection(geometry2);
        System.out.println(interPoint.getNumPoints());
        Coordinate[] pp = interPoint.getCoordinates();
        for(int i=0;i< pp.length;i++)
        	System.out.println(pp[i].x+"\t"+pp[i].y);
        System.out.println(interPoint.toText());*/// POINT (0 0)  or MULTIPOINT ((0 0), (5 0))
	}

}