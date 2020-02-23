math.config({
    number: 'BigNumber',
    precision: 20
});

const ZERO = math.BigNumber(0);
const ONE =math.BigNumber(1);
const TWO =math.BigNumber(2);
const THREE =math.BigNumber(3);
const FOUR =math.BigNumber(4);
const FIVE =math.BigNumber(5);
const SIX =math.BigNumber(6);
const EIGHT =math.BigNumber(8);
const SIXTEEN =math.BigNumber(16);

function calc16dSj(d, j) {
    const ACCURACY = math.add(d,10);
    let part1 = ZERO;
    let part2 = ZERO;
    const numJ = math.BigNumber(j);
    for (let k = 0; k <= d; k++) {
        const numK = math.BigNumber(k);
        const t = EIGHT.mul(numK).add(numJ);
        const powTime = d -k;
        const result = math.divide(quickPowMod16(powTime,t),t);
        part1 = part1.add(result);
    }

    for (let k = d + 1; k < ACCURACY; k++) {
        const numK = math.BigNumber(k);
        part2 = part2.add(
            ONE.div(
                math.pow(SIXTEEN,k-d)
                    .mul(
                        EIGHT.mul(numK)
                            .add(numJ)
                    )
            )
        );
    }
    const r = part1.add(part2);
    return r
}

function calc16dPI(d) {
    return FOUR.mul(calc16dSj(d, 1)).add(THREE).sub(TWO.mul(calc16dSj(d, 4)).mod(ONE)).sub(calc16dSj(d, 5).mod(ONE)).sub(calc16dSj(d, 6).mod(ONE)).mod(ONE);
}

function pi(d) {
    return  SIXTEEN.mul(calc16dPI(d)).floor().toString();
}

function quickPowMod16(d,modNum) {
    let sum = ONE;
    let base = SIXTEEN;
    let c = d;
    while (c >0){
        if (c %2 === 1){
            sum = sum.mul(base).mod(modNum);
        }
        base = base.mul(base).mod(modNum);
        c = Math.floor(c/2);
    }
    return sum;
}