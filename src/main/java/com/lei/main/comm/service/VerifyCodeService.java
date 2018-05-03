package com.lei.main.comm.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface VerifyCodeService {
    /**
     * 发送短信验证码
     * @param phone 电话
     * @param code 验证码
     * @return 0成功，22同一个手机号验证码类内容每天最多能获取到10条，33同一个手机号同一验证码模板每30秒只能发送一条，更多返回值见云片网api
     */
    //public int sendSmsCode(String phone, String code);
    /**
     * 使用系统默认字符源生成验证码
     * @param verifySize 验证码长度
     * @return 验证码
     */
    public String generateVerifyCode(int verifySize);
    /**
     * 使用指定源生成验证码
     * @param verifySize 验证码长度
     * @param sources 验证码字符源
     * @return 验证码
     */
    public String generateVerifyCode(int verifySize, String sources);
    /**
     * 生成随机验证码文件,并返回验证码值
     * @param w 图片长
     * @param h 图片宽
     * @param outputFile 目标文件
     * @param verifySize 验证码长度
     * @return 验证码
     * @throws IOException
     */
    public String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException;
    /**
     * 输出随机验证码图片流,并返回验证码值
     * @param w 图片长
     * @param h 图片宽
     * @param os 输出流
     * @param verifySize 验证码长度
     * @return 验证码
     * @throws IOException
     */
    public String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException;
    //生成指定验证码图像文件
    /**
     * 生成指定验证码图像文件
     * @param w 图片长
     * @param h 图片宽
     * @param outputFile 输出文件
     * @param code 验证码
     * @throws IOException
     */
    public void outputImage(int w, int h, File outputFile, String code) throws IOException;
    /**
     * 输出指定验证码图片流
     * @param w 图片长
     * @param h 图片宽
     * @param os 输出流
     * @param code 验证码
     * @throws IOException
     */
    public void outputImage(int w, int h, OutputStream os, String code) throws IOException;
    
}
