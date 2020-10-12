contract Reentrance {
  mapping(address => uint) public balances;

    // 充值
  function donate(address _to) public payable {
    balances[_to] += msg.value;
  }

  // 查看余额
  function balanceOf(address _who) public view returns (uint balance) {
    return balances[_who];
  }

  // 提现
  function withdraw(uint _amount) public {
    if(balances[msg.sender] >= _amount) {
      if(msg.sender.call.value(_amount)()) { //造成可重入攻击的代码
        _amount;
      }
      balances[msg.sender] -= _amount;
    }
  }

  function() public payable {}
}