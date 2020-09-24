package com.cav.clairvoyance.utils;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;



public class SHA1Test {


    // String SHA1
    @Test
    public void getSHA1String() {
        String str = "contract Reentrance {\n" +
                "  mapping(address => uint) public balances;\n" +
                "\n" +
                "    // 充值\n" +
                "  function donate(address _to) public payable {\n" +
                "    balances[_to] += msg.value;\n" +
                "  }\n" +
                "\n" +
                "  // 查看余额\n" +
                "  function balanceOf(address _who) public view returns (uint balance) {\n" +
                "    return balances[_who];\n" +
                "  }\n" +
                "\n" +
                "  // 提现\n" +
                "  function withdraw(uint _amount) public {\n" +
                "    if(balances[msg.sender] >= _amount) {\n" +
                "      if(msg.sender.call.value(_amount)()) { //造成可重入攻击的代码\n" +
                "        _amount;\n" +
                "      }\n" +
                "      balances[msg.sender] -= _amount;\n" +
                "    }\n" +
                "  }\n" +
                "\n" +
                "  function() public payable {}\n" +
                "}";
        String digest = SHA1.getSHA1String(str);
        System.out.println(digest);


    }

    // File SHA1
    @Test
    public void testGetSHA1String() throws FileNotFoundException {
        String filePath = "/Users/jason/files/reentrancy.sol";
        File file = new File(filePath);
        try {
            String digest = SHA1.getSHA1String(file);
            System.out.println(digest+"\t"+file.getAbsolutePath());
        } catch (FileNotFoundException fn) {
            throw new RuntimeException("Error opening file "+file.getAbsolutePath(), fn);
        }
    }
}