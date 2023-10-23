enum class Spaces{
    MOTORBIKE,CAR,LARGE
}

data class SpaceTypeAndAmount(val spaceType: Spaces,val amount: Int)

abstract class Vehicle {
    open val spacesNeeds = emptyArray<SpaceTypeAndAmount>()
}

class Car : Vehicle() {
    override val spacesNeeds = arrayOf(SpaceTypeAndAmount(Spaces.CAR, 1), SpaceTypeAndAmount(Spaces.LARGE, 1))

    override fun toString(): String {
        return "Car"
    }
}
class MotorBike : Vehicle() {
    override val spacesNeeds =
        arrayOf(SpaceTypeAndAmount(Spaces.MOTORBIKE, 1), SpaceTypeAndAmount(Spaces.CAR, 1), SpaceTypeAndAmount(Spaces.LARGE, 1))

    override fun toString(): String {
        return "MotorBike"
    }
}

class Van : Vehicle() {
    override val spacesNeeds = arrayOf(SpaceTypeAndAmount(Spaces.LARGE, 1),SpaceTypeAndAmount(Spaces.CAR, 3) )
    override fun toString(): String {
        return "Van"
    }
}

data class Ticket(val vehicle: Vehicle, val spaceTypeAndAmount: SpaceTypeAndAmount)

class Parking{
    private val subParkings = mutableMapOf<Spaces,Int>()
    private val subParkingsTotal = mutableMapOf<Spaces,Int>()
    private val tickets = mutableListOf<Ticket>()
    
    fun addSubParking(space:Spaces,lots:Int){
        subParkings[space] = lots
        subParkingsTotal[space] = lots
    }

    fun park(vehicle:Vehicle){
        var parked = false
        vehicle.spacesNeeds.forEach { spaceTypeAndAmount ->
            var lotsAvailable = subParkings[spaceTypeAndAmount.spaceType]
            if (lotsAvailable != null) {
                if(lotsAvailable >= spaceTypeAndAmount.amount){
                    lotsAvailable -= spaceTypeAndAmount.amount
                    subParkings[spaceTypeAndAmount.spaceType] = lotsAvailable
                    parked = true
                    tickets.add(Ticket(vehicle,spaceTypeAndAmount))
                    return
                }
            }
        }
        if(parked){
            return
        } else {
            println("Não possivel estacionar esse veículos")
            return
        }
    }

    fun parkingSummary(){
        println("Spaces Available: ${subParkings.values.sum()})")
        subParkings.forEach {
            println("- Spaces for ${it.key} : ${it.value}")
        }
    }
    
    fun listOfVehiclesInParking(){
        println(tickets)
    }

    fun unpark(vehicle:Vehicle){
        val ticket = tickets.firstOrNull { it.vehicle == vehicle }
        ticket?.let{
            val lotsAvailable = subParkings[ticket.spaceTypeAndAmount.spaceType]
            lotsAvailable?.let {
                subParkings[ticket.spaceTypeAndAmount.spaceType] = it + ticket.spaceTypeAndAmount.amount
                tickets.remove(ticket)
            }
        }
    }

    fun parkingIsEmpty() = tickets.size == 0

    fun parkingIsFull() :Boolean {
        var isFull = true
        subParkings.forEach {
            if(it.value !=0){
                isFull = false
            }
        }
        return isFull
    }

    fun subParkingIsFull(space:Spaces) = subParkings[space] == 0

    fun spacesOfSubParkingCurrentUsed(space:Spaces):Int{
        subParkingsTotal[space]?.let{
            return it - (subParkings[space] ?: 0)
        }
        return 0
    }

    fun spacesUsedForVehicleType(vehicle: Vehicle):Int{
        var sum = 0
        tickets.forEach {
            if(it.vehicle::class == vehicle::class){
                sum += it.spaceTypeAndAmount.amount
            }
        }
        return sum
    }
    
}

fun main() {
    val parking = Parking()
    val car1 = Car()
    val car2 = Car()
    val car3 = Car()
    val motorbike1 = MotorBike()
    val motorbike2 = MotorBike()
    val motorbike3 = MotorBike()
    val van1 = Van()
    val van2 = Van()
    val van3 = Van()
    parking.addSubParking(Spaces.CAR, 1)
    parking.addSubParking(Spaces.MOTORBIKE,1)
    parking.addSubParking(Spaces.LARGE,1)
    parking.park(motorbike1)
    parking.park(car1)
    parking.park(van1)
    parking.parkingIsFull()
    parking.parkingIsEmpty()
    parking.parkingSummary()
    parking.subParkingIsFull(Spaces.CAR)
    parking.unpark(car1)
    parking.listOfVehiclesInParking()
    parking.spacesUsedForVehicleType(MotorBike())
}

main()
