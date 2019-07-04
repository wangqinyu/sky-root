package com.sky.skyServer;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.zxing.WriterException;
import com.sky.skyentity.bean.QRCodeParams;
import com.sky.skyserver.util.qrcode.util.ColorUtil;
import com.sky.skyserver.util.qrcode.util.NumUtil;
import com.sky.skyserver.util.qrcode.wrapper.QrCodeGenWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {
    @Test
    public void contextLoads() {
    }

    @Test
    public void function(){
        System.out.println("11");
    }

/**
     * 07版本excel读数据量少于1千行数据，内部采用回调方法.
     *
     * @throws IOException 简单抛出异常，真实环境需要catch异常,同时在finally中关闭流
     */

    @Test
    public void simpleReadListStringV2007() throws IOException {
        InputStream inputStream = FileUtil.getResourcesFileInputStream("2007.xlsx");
        List<Object> data = EasyExcelFactory.read(inputStream, new Sheet(1, 0));
        inputStream.close();
        if (data != null) {
            System.out.println(data.get(0));
        }
    }

/**
     * 每行数据是List<String>无表头
     *
     * @throws IOException
     */

    @Test
    public void writeWithoutHead() throws IOException {
        try (OutputStream out = new FileOutputStream("withoutHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, false);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<String> item = new ArrayList<>();
                item.add("item0" + i);
                item.add("item1" + i);
                item.add("item2" + i);
                data.add(item);
            }
            writer.write0(data, sheet1);
            writer.finish();
        }
    }


    @Test
    public void writeWithHead() throws IOException {
        try (OutputStream out = new FileOutputStream("withHead.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("sheet1");
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                List<String> item = new ArrayList<>();
                item.add("item0" + i);
                item.add("item1" + i);
                item.add("item2" + i);
                data.add(item);
            }
            List<List<String>> head = new ArrayList<List<String>>();
            List<String> headCoulumn1 = new ArrayList<String>();
            List<String> headCoulumn2 = new ArrayList<String>();
            List<String> headCoulumn3 = new ArrayList<String>();
            headCoulumn1.add("第一列");
            headCoulumn2.add("第二列");
            headCoulumn3.add("第三列");
            head.add(headCoulumn1);
            head.add(headCoulumn2);
            head.add(headCoulumn3);
            Table table = new Table(1);
            table.setHead(head);
            writer.write0(data, sheet1, table);
            writer.finish();
        }
    }


    @Test
    public void writeWithHead2() throws IOException {
        try (OutputStream out = new FileOutputStream("withHead2.xlsx");) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 0, ExcelPropertyIndexModel.class);
            sheet1.setSheetName("sheet1");
            List<ExcelPropertyIndexModel> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                ExcelPropertyIndexModel item = new ExcelPropertyIndexModel();
                item.name = "name" + i;
                item.age = "age" + i;
                item.email = "email" + i;
                item.address = "address" + i;
                item.sax = "sax" + i;
                item.heigh = "heigh" + i;
                item.last = "last" + i;
                data.add(item);
            }
            writer.write(data, sheet1);
            writer.finish();
        }
    }

    public static class ExcelPropertyIndexModel extends BaseRowModel {

        @ExcelProperty(value = "姓名", index = 0)
        private String name;

        @ExcelProperty(value = "年龄", index = 1)
        private String age;

        @ExcelProperty(value = "邮箱", index = 2)
        private String email;

        @ExcelProperty(value = "地址", index = 3)
        private String address;

        @ExcelProperty(value = "性别", index = 4)
        private String sax;

        @ExcelProperty(value = "高度", index = 5)
        private String heigh;

        @ExcelProperty(value = "备注", index = 6)
        private String last;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSax() {
            return sax;
        }

        public void setSax(String sax) {
            this.sax = sax;
        }

        public String getHeigh() {
            return heigh;
        }

        public void setHeigh(String heigh) {
            this.heigh = heigh;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }

   /* @Test
    public void read() throws Exception {
        try (InputStream in = new FileInputStream("withoutHead.xlsx");) {
            AnalysisEventListener<List<String>> listener = new AnalysisEventListener<List<String>>() {

                @Override
                public void invoke(List<String> object, AnalysisContext context) {
                    System.err.println("Row:" + context.getCurrentRowNum() + " Data:" + object);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) {
                    System.err.println("doAfterAllAnalysed...");
                }
            };
            ExcelReader excelReader = ExcelReaderFactory.getExcelReader(in, null, listener);
            excelReader.read();
        }
    }*/

   @Test
    public void HexToInt(){
       /*int a = 0xFF87CEFA;
       String b = "0xFF87CE";
       String c = "0xFF87CEFA";
       Color color = Color.decode(b);
       System.out.println(color);
       Color integer = ColorUtil.int2color(NumUtil.decode2int(c, 16));
       System.out.println(integer);
       QRCodeParams qrCodeParams = new QRCodeParams();
       qrCodeParams.setContent("xxx");
       qrCodeParams.setBgColor(a);
       qrCodeParams.setPreColor(0xFFFFFFFF);
       qrCodeParams.setSize(300);
       qrCodeParams.setStyle("RECT");
       try {
           String bufferedImage = QrCodeGenWrapper.of(qrCodeParams.getContent())
                   .setW(qrCodeParams.getSize())
                   .setH(qrCodeParams.getSize())
                   .setDrawBgColor(new Color(0,0,0))
                   .setDrawPreColor(color)
                   .setDrawStyle(qrCodeParams.getStyle())
                   .asString();
           System.out.println(bufferedImage);
       } catch (IOException e) {
           e.printStackTrace();
       } catch (WriterException e) {
           e.printStackTrace();
       }*/



       //Color color = Color.getColor(b);

       /*int i = Integer.parseUnsignedInt(b,16);
       if (a == i)
           System.out.println("======");
       else
           System.out.println("////");
       System.out.println("a:"+a);
       System.out.println("b:"+b);
       System.out.println("i:"+i);*/
   }

    /**
     * 字符串转换成Color对象
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     * */
    public static Color toColorFromString(String colorStr){
        colorStr = colorStr.substring(4);
        Color color =  new Color(Integer.parseUnsignedInt(colorStr, 16)) ;
        //java.awt.Color[r=0,g=0,b=255]
        return color;
    }
}
