import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import CssBaseline from '@mui/material/CssBaseline';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import List from '@mui/material/List';
import Typography from '@mui/material/Typography';
import Divider from '@mui/material/Divider';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import InboxIcon from '@mui/icons-material/MoveToInbox';
import MailIcon from '@mui/icons-material/Mail';
import { Paper ,Grid} from '@mui/material';
import Button from '@mui/material/Button';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import DownloadIcon from '@mui/icons-material/Download';
import BarChart from './BarChart'
import UploadDialog from './UploadDialog'
import BarChartIcon from '@mui/icons-material/BarChart';
import TextField from '@mui/material/TextField';
import InputAdornment from '@mui/material/InputAdornment';
import SearchIcon from '@mui/icons-material/Search';

// import Paper from '@mui/core/Paper';
import {
    ArgumentAxis,
    ValueAxis,
    Chart,
    BarSeries,
} from '@devexpress/dx-react-chart-material-ui';


const drawerWidth = 240;

export  function PermanentDrawerLeft() {
    const [userList,setUserList]=React.useState([])
    const [userName , setUserName]=React.useState("")
    const [graphData,setGraphData]=React.useState(
        {
            "labels": [
                
            ],
            "datasets": [
                
            ]
        }
    )
    let ColorList=
        [
            "yellow",
            "green",
            "red",
            "purple",
            "pink",
            "aura"
            
        ]
    
    const createDataset=(resList)=>{
        let lables=[];
        let datasets={};
        resList.forEach((item)=>{
            lables.push(item.courseName);
            console.log(item.courseData)
            const sortedData = Object.entries(item.courseData)
                .map(([key, value]) => {
                    const dateString = key.split("\n")[0];
                    const dateParts = dateString.split(" ");
                    const day = parseInt(dateParts[0]);
                    const month = dateParts[1];
                    const year = new Date().getFullYear(); // Assuming the year is the current year
                    const date = new Date(`${month} ${day}, ${year}`);
                    return { key, value, date };
                })
                .sort((a, b) => a.date - b.date)
                .reduce((obj, { key, value }) => {
                    obj[key] = value;
                    return obj;
                }, {});

                console.log(sortedData);
            let tempValue=0;
            for(var obj in sortedData ){
              
                if(datasets[obj]){
                    datasets[obj].data.push(parseFloat(sortedData[obj]))
                    tempValue=parseFloat(sortedData[obj])
                }else{
                    datasets[obj]={
                        
                            label: obj,
                            data: [parseFloat(sortedData[obj])],
                            backgroundColor: "yellow",
                            borderColor: "red",
                          
                    }
                    tempValue=parseFloat(sortedData[obj])
                }
            }
        })
        var lstDataSet=[];
        let ind=0;
        for(var key in datasets){
            datasets[key].backgroundColor=ColorList[ind]
            lstDataSet.push(datasets[key])
            ind+=1
        }
        console.log(lables,datasets)
        let tlstDataSet= lstDataSet
        console.log([...lstDataSet])
        var tempList=lstDataSet[0].data
        for(let ind=1;ind<lstDataSet.length;ind++){
          let tempLst=[...lstDataSet[ind].data]
          let tempSub=tlstDataSet[ind-1].data
          for(let dind=0;dind<tempLst.length;dind++){
            lstDataSet[ind].data[dind]=tempLst[dind]-tempList[dind]
          }
          tempList=[...tempLst]
          // lstDataSet[ind].data=[...tempLst]
        }
        console.log({
            labels:lables,
            datasets:lstDataSet
        })
        setGraphData({
            labels:lables,
            datasets:lstDataSet
        })

    }
    React.useEffect(()=>{
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
          };
          
          fetch(`${process.env.REACT_APP_BACKEND_ENDPOINT}/getAllEmployeeName`, requestOptions)
            .then(response => response.json())
            .then(result => {
                setUserList([...result])
                console.log(typeof(result),result)
            })
            .catch(error => console.log('error', error));

    },[])
    const fetchCourseData=(e)=>{
        console.log(e.target.textContent)
        setUserName(e.target.textContent)
        var requestOptions = {
            method: 'GET',
            redirect: 'follow'
          };
          
          fetch(`${process.env.REACT_APP_BACKEND_ENDPOINT}/getCoursesInfo?ename=${e.target.textContent}`, requestOptions)
            .then(response => response.json())
            .then(result =>{
                console.log(result)
                createDataset([...result])
            } )
            .catch(error => console.log('error', error));
    }
    const data = [
        { argument: 'Monday', value: 30 },
        { argument: 'Tuesday', value: 20 },
        { argument: 'Wednesday', value: 10 },
        { argument: 'Thursday', value: 50 },
        { argument: 'Friday', value: 60 },
    ];
    function moveMatchingElementsToFront(list, substring) {
      for (let i = list.length - 1; i >= 0; i--) {
        if (list[i].includes(substring)) {
          list.unshift(list.splice(i, 1)[0]);
        }
      }
      return list;
    }
    const [info,setinfo]=React.useState(data)
    const [openUpload,setOpenUpload]=React.useState(false)
    const [searchText,setSearchText]=React.useState("")

  return (
    <Box sx={{ display: 'flex' }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        sx={{ width: `calc(100% - ${drawerWidth}px)`, ml: `${drawerWidth}px` }}
        style={{backgroundColor:"#24b082"}}
      >
        <Toolbar>
          <BarChartIcon></BarChartIcon>
          <Typography variant="h6" noWrap component="div" >Q Check </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          '& .MuiDrawer-paper': {
            width: drawerWidth,
            boxSizing: 'border-box',
            backgroundColor:"#232f3e",
          },
        }}

        variant="permanent"
        anchor="left"
      >

        <Toolbar />
      
        <Divider style={{backgroundColor:"white"}} />
        <TextField
          // label="With normal TextField"
          id="outlined-start-adornment"
          sx={{ m: 1, width: '22ch',backgroundColor:"white",color:"#24b082"}}
          InputProps={{
            startAdornment: <InputAdornment position="start">
              <SearchIcon/>
            </InputAdornment>,
          }}
          onChange={(e)=>{
            console.log(e.target.value,e)
            // setSearchText(e.target.value)
            let tempList=moveMatchingElementsToFront([...userList],e.target.value)
            setUserList([...tempList])

          }}
        />
        <List>
          {userList.map((text, index) => (
            <ListItem key={text} style={{color:"white",backgroundColor:userName==text?"#24b082":"#232f3e"}} value={text} disablePadding onClick={fetchCourseData}>
              <ListItemButton>
                <ListItemText primary={text} />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
        <Divider />
        
      </Drawer>
      <Box
        component="main"
        sx={{ flexGrow: 1, bgcolor: 'background.default', p: 3 }}
      >
        <Toolbar />
        <UploadDialog openUpload={openUpload} setOpenUpload={setOpenUpload}></UploadDialog>
        <BarChart graphData={graphData} userName={userName} setOpenUpload={setOpenUpload}/>
      </Box>
    </Box>
  );
}