var app = new Vue({
    el: '#container',
    data: {
      inputUsd: 0,
      nation:[
          {
              name: 'korea (KRW) ',
              code: 'USDKRW',
              unit: 'KRW/USD',
              exRate: 0,
          },
          {
              name: 'japan (JPY) ',
              code: 'USDJPY',
              unit: 'JPY/USD',
              exRate: 0,
          },
          {
              name: 'Philippines (PHP) ',
              code: 'USDPHP',
              unit: 'PHP/USD',
              exRate: 0,
          }
      ],
      exchangeRate:{},
      targetCode:'None',
      targetRate:'',
      targetUnit:'',
      targetNum:0,
      exResult: 0,
      exUnit:'',
      errMsg:'',
    },
    methods:{
        async getExchangeRate() {

            const response = await fetch("/exchangeRates");
            const data = response.json();
            const exchangeData = await data;
            return exchangeData.quotes;

        },
        async parseData() {

            const quotes = await this.getExchangeRate();
            let temp;

            for(value in quotes){
                
                temp = this.nation.findIndex( nationVal => nationVal.code == value);

                if(temp != -1){
                    this.nation[temp].exRate = this.insertDot(quotes[value])
                    this.nation[temp].realNum = parseFloat(Math.round(quotes[value] * 100) / 100);
                    temp = null;
                }

            }
        },
        insertDot(value) {
            let target = Math.round(value * 100) / 100;
            return target.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
        },
        chageRate(){
            
            for( value in this.nation){
                
                if( this.nation[value].code == this.targetCode ){

                    this.targetRate = this.nation[value].exRate;
                    this.targetNum = this.nation[value].realNum;
                    this.targetUnit = this.nation[value].unit;

                }

            }
        },
        async getExResult(){
            this.clearData();

            const response = await fetch('/calculation?' + new URLSearchParams({calData: this.inputUsd, targetNum: this.targetNum }))
            const tempData = response.json();
            const  data = await tempData;

            const error = await data.error;
            if( error ){
                console.info(error);
                return;
            }

            if( data.errMsg != null ){
                this.errMsg = data.errMsg;
                return;
            }

            this.exResult = this.insertDot(data.calculationData);
            this.exUnit   = this.targetUnit.substr(0, 3);
        },
        clearData() {
            this.exResult = null;
            this.errMsg = null;
        }
    },
    created() {
        this.parseData();
    }
  })