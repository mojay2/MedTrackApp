# MedTrackBackend
## Progress
- [x] Database structure
- [x] Dummy ui
- [x] Navigation
- [x] Create medicine & program records 
- [x] Read medicine & program records
- [x] Update func. for all records
- [x] Delete func. for all records
- [x] Add Time Entries on program create
- [x] Display Medicine to take on home page
- [x] Separate taken medicine from upcoming
## Final Todo
### Overall Fixes
- [X] Back Button on headers not working
- [x] Bottom NavBar active tab indicator not working (because route class in unused)

### Add edit medicine screen
- [ ] When updating, forms are not filled
- [ ] Make snackbars/toast on success and on invalid details 
- [ ] No logic for setting active/inactive

### Add edit program screen
- [ ] no multiple times (nung sinubukan ko gawin, pare pareho lagi yung time for all the fields, will try
 again after prio fixes)
- [ ] When updating, forms are not filled (on my prio)
- [ ] Make snackbars/toast on success and on invalid details 

### Medicine Details Screen
- [x] No logic for active and inactive programs, currently, the first program found is the active one (on my prio)
- [x] No calculation for weeks left, and program end date.

### Medicine Cabinet Screen
- [x] Active Medicine functionality is not used

### Home Screen
- [ ] Didnt use floating action button and active medicine. An embedded class
is used to populate the home screen (yung mga inner join stuff), which is hard to "set active"
- [ ] taking med must be disabled if medicine quantity will be negative after (w/ snackbar)


