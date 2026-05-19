package cs112.ud3;

/**
 * Concrete child class representing a specific income transaction.
 * Inherits from Transaction class.
 *
 * @author Rohith Mekala
 * @version 1.0
 */
public class Income extends Transaction {
    // CONSTANT VARIABLES
    public static final boolean DEFAULT_IS_PASSIVE = false;

    // INSTANCE VARIABLES
    private boolean isPassive;

    // CONSTRUCTORS
    /**
     * Default Constructor. Invokes parent defaults and sets passive status to false.
     */
    public Income() {
        super();
        this.isPassive = DEFAULT_IS_PASSIVE;
    }

    /**
     * Full Constructor, validates ranges before applying arguments (validates
     * parent fields via super constructor).
     * * @param description     Summary of transaction
     * @param amount          Monetary value of transaction (must be positive)
     * @param category        Category of transaction (examples: Salary, Investments, etc.)
     * @param isPassive       True if the income is earned passively with minimal active effort
     */
    public Income(String description, double amount, String category, boolean isPassive) {
        super(description, amount, category);
        this.isPassive = isPassive;
    }

    /**
     * Copy Constructor. Prevents shallow copying of object data references.
     * * @param original Income object to duplicate
     */
    public Income(Income original) {
        super(original); // Passes original to parent copy constructor
        if (original == null) {
            throw new IllegalArgumentException("Cannot copy a null Income object.");
        }
        this.isPassive = original.isPassive;
    }

    // SETTERS
    /**
     * Sets whether or not income is passive.
     * * @param isPassive True if the income qualifies as passive streams
     * @return Always returns true because booleans have no invalid state limits
     */
    public boolean setIsPassive(boolean isPassive) {
        this.isPassive = isPassive;
        return true;
    }

    // GETTERS
    /**
     * Get whether or not the transaction is passive income.
     * * @return isPassive as either true or false.
     */
    public boolean getIsPassive() {
        return isPassive;
    }

    // REQUIRED METHODS
    /**
     * String of all instance variables (category, description, amount and
     * isPassive)
     * * @return String containing category, description, amount and isPassive,
     * in this format:
     * Category: [General] "Generic description" + | Amount: 0.00$ + " | Passive Income: NO"
     */
    @Override
    public String toString() {
        return super.toString() + " | Passive Income: " + (this.isPassive ? "YES" : "NO");
    }

    /**
     * Checking for equality of Income objects, all instance variables exactly
     * equal to each other (ignoring case). Uses super to compare core transaction data.
     * * @param other Income object to compare for equality
     * * @return boolean representing equality between both objects, all data is
     * exactly equal to each other
     */
    @Override
    public boolean equals(Object other) {
        // Check if the parent class thinks the core transaction data matches
        if (!super.equals(other)) {
            return false;
        }
        // Safely cast to checking the child variable state
        Income otherIncome = (Income) other;
        return this.isPassive == otherIncome.isPassive;
    }
}