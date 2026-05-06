function StatusBadge({ status }) {
    const paid = status === "Paid";
    return (
        <span className={`px-2.5 py-1 rounded-full text-xs font-semibold ${paid ? "bg-emerald-50 text-emerald-700" : "bg-orange-50 text-orange-700"}`}>
      {status}
    </span>
    );
}

export default StatusBadge;