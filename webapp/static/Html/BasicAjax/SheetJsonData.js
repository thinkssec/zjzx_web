TreeGridLoaded ({ // JSONP header, to be possible to load from xxx_Jsonp data source
Cfg: {
   AutoRows:"2",          // Adds two empty rows to the end of grid
   AutoCols:"13",         // Generates 13 variable columns according to ColIndex, from A to M
   FocusedRect:"2,C,8,E", // Actually focused area in grid on start
   Focused:"5", FocusedCol:"D" // Actually editable cell inside focused area
   }, // Cfg
Body : [[
   { A:'=sum(2:8,B:H)' },
   { B:'Relative', C:'=sum(C3:C7)', D:'=sum(D3:D7)', E:'=sum(E3:E7)' },
   { B:'=sum(C3:G3)', C:'1', D:'6', E:'11', F:'16', G:'21', H:'=sum($C$3:$G$3)' },
   { B:'=sum(C4:G4)', C:'2', D:'7', E:'12', F:'17', G:'22', H:'=sum($C$4:$G$4)' },
   { B:'=sum(C5:G5)', C:'3', D:'8', E:'13', F:'18', G:'23', H:'=sum($C$5:$G$5)' },
   { C:'4', D:'9', E:'14', F:'19', G:'24' },
   { C:'5', D:'10', E:'15', F:'20', G:'25' },
   { C:'=sum($C$3:$C$7)', D:'=sum($D$3:$D$7)', E:'=sum($E$3:$E$7)', H:'Absolute' },
   { I:'=sum($2:$8,$B:$H)' },
   { C:'=1/0', D:'=xxx+yyy+10', E:'=10-"""' },
   {},
   { C:"C3", D:"D3", E:"E3", ERotate:"1", F:"F3", G:"G3", H:"H3", I:"ABCDEFG 1234567890 abcdefgh", IWrap:"1" },
   { C:"C4", D:"D4", E:"E4", F:"F4", G:"G4", H:"H4", HTextShadowColor:"red", DSpan:"3", DRowSpan:"2", DBorderLeft:"1,black", DBorderTop:"1,black", EBorderTop:"1,black", FBorderTop:"1,black", FBorderRight:"1,black" },
   { C:"C5", D:"D5", E:"E5", F:"F5", G:"G5", H:"H5", HTextShadow:"8", HTextShadowColor:"red", DBorderLeft:"1,black", DBorderBottom:"1,black", EBorderBottom:"1,black", FBorderBottom:"1,black", FBorderRight:"1,black" },
   { C:"C6", D:"D6", E:"E6", F:"F6", G:"G6", GCanEdit:"0", H:"H6", HCanEdit:"2", HTextShadow:"9", CBorderTop:"3,black", DBorderTop:"3,black", EBorderTop:"3,black", FBorderTop:"3,black", GBorderTop:"3,black", HBorderTop:"3,black" },
   { C:"C7 XXXX AAAA BBBB", Wrap:"1", D:"D7", E:"E7", F:"F7", G:"G7", H:"H7", CTextShadow:"9", CTextShadowColor:"red", DBorderBottom:"3,blue,1", EColor:"#F88" }
   ]] // Body
}) // End of JSONP header