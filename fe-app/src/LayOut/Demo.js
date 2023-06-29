// import { React.useEffect, React.useState } from 'react';
import * as React from 'react';

import './Demo.css'
export const  ButtonColumns = () => {
    const [score,setScore]=React.useState(0)

    // let disableQ[]
    let colA=["Maharastra","Rajasthan","UP","West Bengal","Karnataka","Gujrat","Goa"]
    let colB=["Lucknow","Gandhinagar","Panaji","Jaipur","Bengaluru","Mumbai","Kolkata"]
    let answer=[5,3,0,6,4,1,2]
    const [userAns,setUserAns]=React.useState({})
    const[disableQ,setDisableQ]=React.useState(new Array(colA.length).fill(false));
    const[disableA,setDisableA]=React.useState(new Array(colA.length).fill(false));
    const [infoMsg,setInfoMsg]=React.useState(false)
    const [disableColA,setDisableColA]=React.useState(false)
    const [disableColB,setDisableColB]=React.useState(true)
    const [key,setKey]=React.useState("")
    const [count,setCount]=React.useState(colA.length)
    const [result,setResult]=React.useState(null)
    const clickOnColumnA=(item,index)=>{
        let tempDis=[...disableQ]
        tempDis[index]=true
        setDisableQ(tempDis)
        setInfoMsg(true)
        setDisableColA(true)
        setDisableColB(false)
        setKey(index)
    }
    const clickOnColumnB=(item,index)=>{
        let tempDis=[...disableA]
        tempDis[index]=true
        setDisableA(tempDis)
        setInfoMsg(false)
        setDisableColA(false)
        setDisableColB(true)
        let temDict={...userAns,[key]:index}
        setUserAns(temDict)
        setCount((prev)=>prev-1)
    }
    React.useEffect(()=>{
        if(count==0){
            console.log(userAns,count)
            let score=0
            for(var key in userAns){
                console.log("key",key)
                if(answer[key]==userAns[key])
                {
                    score+=1
                }

            }
            console.log(score)
            setResult(score)
        }

    },[count==0])

      return (
        <div>
        <div className="button-columns">
          <div className="column">
            <h2>State</h2>
            <div className="button-container">
            {colA.map((item,index)=>(
              <button onClick={()=>clickOnColumnA(item,index)} disabled={disableQ[index] || disableColA}>{item}</button>
          ))}

            </div>
          </div>
          <div className="column">
            <h2>Capital</h2>
            <div className="button-container">
            {colB.map((item,index)=>(
              <button onClick={()=>clickOnColumnB(item,index)} disabled={disableA[index] || disableColB}>{item}</button>
          ))}
            </div>
          </div>
          
        </div>
        <div>{ infoMsg &&
            <p style={{color:"red"}}>Select capital for {colA[key]}</p>
        }
          </div>
          <div>{ result &&
            <h1 style={{color:"green"}}>Your Score is {result} </h1>
        }
          </div>
        </div>
      );
    };