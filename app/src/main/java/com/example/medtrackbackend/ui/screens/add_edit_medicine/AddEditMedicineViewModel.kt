package com.example.medtrackbackend.ui.screens.add_edit_medicine

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.medtrackbackend.ui.repository.Repository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.medtrackbackend.Graph
import com.example.medtrackbackend.data.IntakeProgram
import com.example.medtrackbackend.data.IntakeTimesWithProgramAndMedicine
import com.example.medtrackbackend.data.Medicine
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddEditMedicineViewModel
    constructor(
    private val repository: Repository = Graph.repository,
    private var medicineId:Int,
    ): ViewModel(){
    var state by mutableStateOf(AddEditMedicineState())
        private set

    init {
        getAllMedicine()
        getAllPrograms()
        if(medicineId != -1){
            viewModelScope.launch {
                repository
                    .getMedicineById(medicineId)
                    .collectLatest {
                        if(it != null){
                            state = state.copy(
                                editedName = it.medicineName,
                                editedQty = it.quantity.toString(),
                                editedDosage = it.dosage.toInt().toString()
                            )
                        } else {
                            medicineId = -1
                        }
                    }
            }
        }
    }

    private fun getAllMedicine(){
        viewModelScope.launch {
            repository.allMedicine.collectLatest {
                state = state.copy(
                    medicine = it ?: emptyList()
                )
            }
        }
    }

    private fun getAllPrograms(){
        viewModelScope.launch {
            repository.allPrograms.collectLatest {
                state = state.copy(
                    programs = it
                )
            }
        }
    }

    private fun insertMedicine(medicineName: String, qty: Int, dosage:Double){
        viewModelScope.launch {
            repository.insertMedicine(
                Medicine(
                    medicineName = medicineName,
                    quantity = qty,
                    dosage = dosage,
                    isActive = false
                )
            )
        }
    }

    private fun updateMedicine(id: Int, medicineName: String, qty: Int, dosage:Double, isActive:Boolean){
        viewModelScope.launch {
            repository.updateMedicine(
                Medicine(
                    id = id,
                    medicineName = medicineName,
                    quantity = qty,
                    dosage = dosage,
                    isActive = isActive
                )
            )
        }
    }

    fun getMedicine(medicineId: Int) {
        viewModelScope.launch {
            repository.getMedicineById(medicineId).collectLatest {
                state = state.copy(
                    selectedMedicine = it?: Medicine(999,"",999, 999.9, false),
                )
            }
        }
    }

     fun deleteMedicine(medicineId: Int){
        viewModelScope.launch{
            try{
                repository.deleteMedicineFromId(medicineId)
                Log.e("AddEditMedicineVM", "Deleted Medicine Successfully")
            } catch(e: Exception) {
                Log.e("AddEditMedicineVM", "Delete Medicine failed: ${e.message}", e)
            }
        }
    }

    fun onNameChange(newValue: String){
        Log.d("AddEditMedVM", "onName called with $newValue")
        state = state.copy(
            editedName = newValue
        )
    }

    fun onQtyChange(newValue:String){
        Log.d("AddEditMedVM", "onQtyChange called with $newValue")
        try {
            state = state.copy(editedQty = newValue)
        } catch (e: NumberFormatException) {
            Log.e("AddEditMedVM", "Error Updating Quantity: ${e.message}", e)
        }
    }

    fun onDosageChange(newValue:String){
        Log.d("AddEditMedVM", "onWeekChange called with $newValue")
        try {
            state = state.copy(editedDosage = newValue)
        } catch (e: NumberFormatException) {
            Log.e("AddEditMedVM", "Error Updating Dosage: ${e.message}", e)
        }
    }

    fun validateInputs():Boolean {
        val qtyAsInt = state.editedQty.toIntOrNull()
        val dosageAsInt = state.editedDosage.toIntOrNull()

        if(state.editedName == "" || state.editedQty == "" || state.editedDosage == "" ||
            qtyAsInt == null || dosageAsInt == null){
            return false
        }
        return true
    }

    fun insertStateInputs():Boolean {
        return try {
            if(state.selectedMedicine.id === 999){
                insertMedicine(
                    state.editedName,
                    state.editedQty.toInt(),
                    state.editedDosage.toDouble()
                )
            } else {
                updateMedicine(
                    state.selectedMedicine.id,
                    state.editedName,
                    state.editedQty.toInt(),
                    state.editedDosage.toDouble(),
                    state.selectedMedicine.isActive
                )
            }

            true
        } catch(e: Exception){
            Log.d("AddEditMedVM", "Error Calling Insert Function")
            false
        }
    }

}
@Suppress("UNCHECKED_CAST")
class AddEditMedicineViewModelFactory(private val id:Int): ViewModelProvider.Factory{
    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        return AddEditMedicineViewModel(medicineId = id) as T
    }
}
data class AddEditMedicineState(
    val medicine: List<Medicine> = emptyList(),
    val programs: List<IntakeProgram> = emptyList(),
    val selectedMedicine : Medicine = Medicine(999, "", 999,
        999.9, false),
    val editedName: String = "",
    val editedQty: String = "",
    val editedDosage: String = ""
)