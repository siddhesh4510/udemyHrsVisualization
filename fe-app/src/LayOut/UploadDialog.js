import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import Slide from '@mui/material/Slide';

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

export default function AlertDialogSlide(props) {
console.log(props)
  const [open, setOpen] = React.useState(props.openUpload);
  React.useEffect(()=>{
    setOpen(props.openUpload)
    props.setOpenUpload(props.openUpload)
  },[props.openUpload])

  const handleClickOpen = () => {
    setOpen(true);
    props.setOpenUpload(true)
  };

  const handleClose = () => {
    setOpen(false);
    props.setOpenUpload(false)
    setUploaded(false)
    // setSelectedFile(null)
    // setFileName("")
  };
  const [selectedFile, setSelectedFile] =React.useState(null);
  const [fileName,setFileName]=React.useState("")
  const [uploaded,setUploaded]=React.useState(false)

  const handleFileChange = (event) => {
    console.log(event.target.files[0])
    setSelectedFile(event.target.files[0]);
    setFileName(event.target.files[0].name)
  };

  const handleUpload = () => {
    if (selectedFile) {
      const formData = new FormData();
      formData.append('file', selectedFile);
      var requestOptions = {
        method: 'POST',
        body: formData,
        redirect: 'follow'
      };
      
      fetch(`${process.env.REACT_APP_BACKEND_ENDPOINT}/upload`, requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            setUploaded(true)
            // var requestOptions = {
            //     method: 'GET',
            //     redirect: 'follow'
            //   };
              
            //   fetch("${process.env.REACT_APP_BACKEND_ENDPOINT}/fetchDynamoDBdata", requestOptions)
            //     .then(response => response.text())
            //     .then(result => {
            //         console.log(result)
            //         handleClose()
            //     })
            //     .catch(error => console.log('error', error));
        })
        .catch(error => console.log('error', error));
    } else {
      console.log('No file selected!');
    }
  };
  const handleFetch=()=>{
                var requestOptions = {
                method: 'GET',
                redirect: 'follow'
              };
              
              fetch(`${process.env.REACT_APP_BACKEND_ENDPOINT}/fetchDynamoDBdata`, requestOptions)
                .then(response => response.text())
                .then(result => {
                    console.log(result)
                    handleClose()
                })
                .catch(error => console.log('error', error));

  }

  return (
    <div>

      <Dialog
        open={open}
        TransitionComponent={Transition}
        keepMounted
        onClose={handleClose}
        aria-describedby="alert-dialog-slide-description"
        // maxWidth={true}
        fullWidth={"sm"}
        
      >
        <DialogTitle style={{color:"#24b082"}}>{"Upload New File"}</DialogTitle>
        <DialogContent>
        <Button
        variant="contained"
        component="label"
        style={{backgroundColor:"#24b082"}}
        >
        Select File
        <input
            type="file"
            hidden
            onChange={handleFileChange}
        />
        </Button>
        <p style={{color:"#24b082"}}>{fileName}</p>

        </DialogContent>
        <DialogActions>
          <Button style={{color:"#24b082"}} onClick={handleClose}>Cancel</Button>
          <Button style={{color:"#24b082"}} onClick={uploaded?handleFetch:handleUpload}>{uploaded?'Fetch Latest':'Upload'}</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}